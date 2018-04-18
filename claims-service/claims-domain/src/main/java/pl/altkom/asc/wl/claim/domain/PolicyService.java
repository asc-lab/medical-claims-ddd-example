package pl.altkom.asc.wl.claim.domain;

import lombok.RequiredArgsConstructor;
import pl.altkom.asc.wl.claim.domain.port.input.GetPolicyPort;
import pl.altkom.asc.wl.claim.domain.port.input.PolicyDto;
import pl.altkom.asc.wl.claim.domain.port.output.PolicyFromStorageDto;
import pl.altkom.asc.wl.claim.domain.port.output.PolicyRepositoryPort;

import java.util.Optional;

/**
 * @author tdorosz
 */
@RequiredArgsConstructor
class PolicyService implements GetPolicyPort {

    private final PolicyRepositoryPort policyRepository;

    @Override
    public Optional<PolicyDto> get(String policyNumber) {
        Optional<PolicyFromStorageDto> policyFromStorageDto = policyRepository.get(policyNumber);
        //todo @tdorosz:
        return Optional.of(new PolicyDto());
    }
}
