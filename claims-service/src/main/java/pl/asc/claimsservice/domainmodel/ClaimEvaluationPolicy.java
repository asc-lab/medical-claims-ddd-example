package pl.asc.claimsservice.domainmodel;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.asc.claimsservice.shared.primitives.MonetaryAmount;

@RequiredArgsConstructor
public class ClaimEvaluationPolicy {
    private final Policy policy;
    private final LimitConsumptionContainerCollection consumptions;

    boolean claimNotCovered(Claim claim) {
        return !policy.versions().validAtDate(claim.getEventDate()).covers(claim);
    }


    ClaimItemEvaluation evaluateItem(ClaimItem claimItem) {
        EvaluationContext ctx = EvaluationContext.builder()
                .policyVersion(policy.versions().validAtDate(claimItem.getClaim().getEventDate()))
                .claimItem(claimItem)
                .evaluation(ClaimItemEvaluation.paidByInsurer(claimItem))
                .build();

        if (ClaimItemNotCovered.isSatisfied(ctx.policyVersion,ctx.claimItem)) {
            return ClaimItemEvaluation.paidByCustomer(claimItem);
        }

        applyCoPayment(ctx);

        applyLimit(ctx);

        consumptions.registerConsumption(claimItem);

        return ctx.evaluation;
    }

    private void applyLimit(EvaluationContext ctx) {
        Limit limit = ctx.policyVersion.getLimitFor(ctx.claimItem.getServiceCode().getCode());
        LimitConsumptionContainer.Consumed consumed = consumptions
                .getConsumptionFor(
                        ctx.claimItem.getClaim().getPolicyVersionRef().policyRef(),
                        ctx.claimItem.getClaim().getEventDate(),
                        ctx.claimItem.getServiceCode(),
                        limit.getPeriod().calculateDateRange(ctx.claimItem.getClaim().getEventDate(),ctx.policyVersion)
                );
        ctx.evaluation.applyLimit(
                limit.calculate(ctx.claimItem.getQt(), ctx.claimItem.getPrice(), ctx.coPayment, consumed.getQuantity(), consumed.getAmount()));
    }

    private void applyCoPayment(EvaluationContext ctx) {
        MonetaryAmount coPayment = ctx.policyVersion
                .getCoPaymentFor(ctx.claimItem.getServiceCode().getCode())
                .calculate(ctx.claimItem.cost());
        ctx.evaluation.applyCoPayment(coPayment);
        ctx.coPayment = coPayment;
    }

    @Getter
    @Setter
    @Builder
    static class EvaluationContext {
        private PolicyVersion policyVersion;
        private ClaimItem claimItem;
        private MonetaryAmount coPayment;
        private ClaimItemEvaluation evaluation;
    }

}
