package pl.altkom.asc.wl.claim.domain;

import lombok.RequiredArgsConstructor;
import pl.altkom.asc.wl.claim.domain.port.input.ErrorCode;
import pl.altkom.asc.wl.claim.domain.port.input.GenericResponse;
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
//    private final ClaimRepositoryPort claimRepository;

    @Override
    public GenericResponse<String> process(NewClaimCommand newClaimCommand) {
        Optional<PolicyFromStorageDto> policyFromStorage = policyRepository.get(newClaimCommand.getPolicyNumber());

        if(!policyFromStorage.isPresent()) {
            return GenericResponse.error(ErrorCode.CLAIMS_001);
        }

        Policy policy = new PolicyAssembler().from(policyFromStorage.get());

        SubmittedClaim submittedClaim = new SubmittedClaim(policy, newClaimCommand.getEventDateTime());



        if (!submittedClaim.isRejected()) {
            // calculate outpayment amount
            // - check if cover exists in policy
            // calculate

            // check limit

            // reserve limit
        }

        //save claim in repository
//        claimRepository.save(submittedClaim);


        return GenericResponse.success("1234");
    }
}
