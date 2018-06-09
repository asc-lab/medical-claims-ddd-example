package pl.asc.claimsservice.domain;

import pl.asc.claimsservice.commands.registerpolicy.dto.*;
import pl.asc.claimsservice.shared.primitives.DateRange;
import pl.asc.claimsservice.shared.primitives.MonetaryAmount;
import pl.asc.claimsservice.shared.primitives.Percent;
import pl.asc.claimsservice.shared.primitives.Quantity;

public class PolicyFactory {
    public Policy create(PolicyVersionDto policyVersion) {
        Policy policy = new Policy(policyVersion.getPolicyNumber());
        createVersion(policyVersion, policy);
        return policy;
    }

    public Policy createVersion(PolicyVersionDto policyVersion, Policy policy){
        PolicyVersion version = policy.versions().add(
                policyVersion.getVersionNumber(),
                policyVersion.getProductCode(),
                createPolicyHolder(policyVersion.getPolicyHolder()),
                policyVersion.getAccountNumber(),
                DateRange.between(policyVersion.getPolicyValidFrom(), policyVersion.getPolicyValidTo()),
                DateRange.between(policyVersion.getVersionValidFrom(), policyVersion.getVersionValidTo())
        );

        policyVersion.getCovers().forEach(c -> createCover(c,version));
        return policy;
    }

    private void createCover(CoverDto cover, PolicyVersion version) {
        Cover newCover = version.covers().add(cover.getCode());
        cover.getServices().forEach(s -> createService(s, newCover));
    }

    private void createService(ServiceDto service, Cover newCover) {
        Service newService = newCover.services().add(
                service.getCode(),
                createCoPayment(service.getCoPayment()),
                createLimit(service.getLimit())
        );
    }

    private Limit createLimit(LimitDto limit) {
        if (limit==null)
            return null;

        return new Limit(
                LimitPeriod.valueOf(limit.getPeriodTypeCode()),
                limit.getMaxAmount()!=null ? MonetaryAmount.from(limit.getMaxAmount()) : null,
                limit.getMaxQuantity()!=null ? Quantity.of(limit.getMaxQuantity()) : null
        );
    }

    private CoPayment createCoPayment(CoPaymentDto coPayment) {
        if (coPayment==null)
            return CoPayment.empty();

        return coPayment.getPercent()!=null ?
                CoPayment.percent(Percent.of(coPayment.getPercent()))
                : CoPayment.amount(MonetaryAmount.from(coPayment.getAmount()));
    }

    private Person createPolicyHolder(PersonDto person) {
        return new Person(person.getFirstName(), person.getLastName(), person.getPesel());
    }
}
