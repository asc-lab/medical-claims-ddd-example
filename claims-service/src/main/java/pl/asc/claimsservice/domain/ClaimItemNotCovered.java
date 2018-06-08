package pl.asc.claimsservice.domain;

import pl.asc.claimsservice.shared.specification.Specification;

public class ClaimItemNotCovered extends Specification<ClaimItem> {
    static boolean isSatisfied(ClaimItem objectToCheck) {
        return new ClaimItemNotCovered().isSatisfiedBy(objectToCheck);
    }

    @Override
    public boolean isSatisfiedBy(ClaimItem objectToCheck) {
        return !objectToCheck.getClaim().getPolicyVersion().covers().hasCoverForService(objectToCheck.getServiceCode());
    }
}
