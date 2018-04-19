package pl.altkom.asc.wl.claim.domain;

import pl.altkom.asc.wl.claim.domain.port.output.PolicyFromStorageDto;

/**
 * @author tdorosz
 */
class PolicyAssembler {
    public Policy from(PolicyFromStorageDto policyFromStorageDto) {
        return new Policy(policyFromStorageDto.getPolicyNumber(), null);
    }
}
