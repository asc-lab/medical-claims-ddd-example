package pl.asc.claimsservice.domainmodel;

import lombok.RequiredArgsConstructor;
import pl.asc.claimsservice.shared.specification.Specification;

@RequiredArgsConstructor
public class ClaimItemNotCovered extends Specification<ClaimItem> {
    private final PolicyVersion policyVersion;

    static boolean isSatisfied(PolicyVersion policyVersion, ClaimItem objectToCheck) {
        return new ClaimItemNotCovered(policyVersion).isSatisfiedBy(objectToCheck);
    }

    @Override
    public boolean isSatisfiedBy(ClaimItem objectToCheck) {
        return !policyVersion.covers().hasCoverForService(objectToCheck.getServiceCode().getCode());
    }
}
