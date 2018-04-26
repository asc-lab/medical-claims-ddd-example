package pl.altkom.asc.wl.claim.domain;

import lombok.RequiredArgsConstructor;
import pl.altkom.asc.wl.claim.domain.port.input.GetPolicyPort;
import pl.altkom.asc.wl.claim.domain.port.input.PolicyDto;
import pl.altkom.asc.wl.claim.domain.port.output.PolicyVersionDto;
import pl.altkom.asc.wl.claim.domain.port.output.PolicyRepository;

import java.util.Optional;

/**
 * @author tdorosz
 */
@RequiredArgsConstructor
class PolicyService implements GetPolicyPort {

    private final PolicyRepository policyRepository;

    @Override
    public Optional<PolicyDto> get(String policyNumber) {
        Optional<PolicyVersionDto> policyFromStorageDto = policyRepository.lastVersion(policyNumber);
        //todo @tdorosz:
        return Optional.of(new PolicyDto());
    }
}
