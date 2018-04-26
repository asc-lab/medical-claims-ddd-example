package pl.altkom.asc.wl.claim.domain;

import pl.altkom.asc.wl.claim.domain.policy.PolicyVersion;
import pl.altkom.asc.wl.claim.domain.port.input.ErrorCode;
import pl.altkom.asc.wl.claim.domain.port.input.GenericResponse;
import pl.altkom.asc.wl.claim.domain.port.input.NewClaimCommand;
import pl.altkom.asc.wl.claim.domain.port.input.SubmitClaimPort;
import pl.altkom.asc.wl.claim.domain.port.output.ClaimRepository;
import pl.altkom.asc.wl.claim.domain.port.output.PolicyRepository;
import pl.altkom.asc.wl.claim.domain.port.output.PolicyVersionDto;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

/**
 * @author tdorosz
 */
@RequiredArgsConstructor
class SubmitClaimService implements SubmitClaimPort {

    private final PolicyRepository policyRepository;
    private final ClaimRepository claimRepository;

    @Override
    public GenericResponse<String> process(NewClaimCommand cmd) {
        Optional<PolicyVersionDto> policyFromStorage = policyRepository.lastVersion(cmd.getPolicyNumber());

        if (!policyFromStorage.isPresent()) {
            return GenericResponse.error(ErrorCode.CLAIMS_001);
        }

        final PolicyVersion policyVersion = new PolicyAssembler().from(policyFromStorage.get());

        final Incident incident = new Incident(cmd.getEventDateTime(), cmd.getMedicalInstitutionCode(), prepareClaimItems(cmd));
        final SubmittedClaim submittedClaim = new SubmittedClaim(UUID.randomUUID());
        submittedClaim.submitted(policyVersion, incident);

        claimRepository.save(submittedClaim.getUuid(), submittedClaim.getDirtyEvents());

        return GenericResponse.success("1234");
    }

    private Set<ClaimItem> prepareClaimItems(NewClaimCommand cmd) {
        return cmd.getClaimPositions().stream()
                .map(dto -> new ClaimItem(dto.getServiceCode(), dto.getCount(), dto.getAmount()))
                .collect(Collectors.toSet());
    }
}
