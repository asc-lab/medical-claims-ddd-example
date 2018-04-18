package pl.altkom.asc.wl.claim.domain;

import lombok.RequiredArgsConstructor;
import pl.altkom.asc.wl.claim.domain.port.input.NewClaimCommand;
import pl.altkom.asc.wl.claim.domain.port.input.SubmitClaimPort;
import pl.altkom.asc.wl.claim.domain.port.output.PolicyFromStorageDto;
import pl.altkom.asc.wl.claim.domain.port.output.PolicyRepositoryPort;

import java.util.Optional;

/**
 * @author tdorosz
 */
@RequiredArgsConstructor
class SubmitClaimService implements SubmitClaimPort {

    private final PolicyRepositoryPort policyRepository;

    @Override
    public void process(NewClaimCommand newClaimCommand) {
        Optional<PolicyFromStorageDto> policyFromStorageDto = policyRepository.get(newClaimCommand.getPolicyNumber());

        if(!policyFromStorageDto.isPresent()) {
            //todo @tdorosz: return error
            return;
        }

    }
}
