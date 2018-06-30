package pl.asc.claimsservice.domainmodel;

import lombok.RequiredArgsConstructor;
import pl.asc.claimsservice.shared.primitives.MonetaryAmount;

@RequiredArgsConstructor
public class ClaimEvaluationPolicy {
    private final Policy policy;
    private final LimitConsumptionContainerCollection consumptions;

    boolean claimNotCovered(Claim claim) {
        return !policy.versions().validAtDate(claim.getEventDate()).covers(claim);
    }


    ClaimItemEvaluation evaluateItem(ClaimItem claimItem) {
        PolicyVersion policyVersion = policy.versions().validAtDate(claimItem.getClaim().getEventDate());

        //assume insurer pays all
        ClaimItemEvaluation evaluation = ClaimItemEvaluation.paidByInsurer(claimItem);

        //check if covered by policy
        if (ClaimItemNotCovered.isSatisfied(policyVersion,claimItem)) {
            evaluation = ClaimItemEvaluation.paidByCustomer(claimItem);
            return evaluation;
        }

        //apply coPayment
        MonetaryAmount coPayment = policyVersion.getCoPaymentFor(claimItem.getServiceCode().getCode()).calculate(claimItem.cost());
        evaluation.applyCoPayment(coPayment);

        //apply limit
        Limit limit = policyVersion.getLimitFor(claimItem.getServiceCode().getCode());
        LimitConsumptionContainer.Consumed consumed = consumptions
                .getConsumptionFor(
                        claimItem.getClaim().getPolicyVersionRef().policyRef(),
                        claimItem.getClaim().getEventDate(),
                        claimItem.getServiceCode(),
                        limit.getPeriod().calculateDateRange(claimItem.getClaim().getEventDate(),policyVersion)
                );
        evaluation.applyLimit(limit.calculate(claimItem.getQt(), claimItem.getPrice(), coPayment, consumed.getQuantity(), consumed.getAmount()));

        consumptions.registerConsumption(claimItem);

        return evaluation;
    }

}
