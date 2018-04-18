package pl.altkom.asc.wl.claim.app.infra.rest.submitclaim;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.altkom.asc.wl.claim.domain.port.input.NewClaimCommand;
import pl.altkom.asc.wl.claim.domain.port.input.NewClaimPositionDto;
import pl.altkom.asc.wl.claim.domain.port.input.SubmitClaimPort;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tdorosz
 */
@Component
@RequiredArgsConstructor
class SubmitClaimAdapter {

    private final SubmitClaimPort submitClaimPort;

    public void submitNewClaimsForPolicy(String policyNumber, NewClaimRequestDto newClaimRequest) {
        NewClaimCommand newClaimCommand = assembleCommand(policyNumber, newClaimRequest);

        submitClaimPort.process(newClaimCommand);
    }

    private NewClaimCommand assembleCommand(String policyNumber, NewClaimRequestDto newClaimRequest) {
        List<NewClaimPositionDto> claimPositions = newClaimRequest.getClaimPositions().stream()
                .map(cp -> new NewClaimPositionDto(cp.getServiceCode(), cp.getAmount(), cp.getCurrency(), cp.getCount()))
                .collect(Collectors.toList());

        return new NewClaimCommand(
                policyNumber, newClaimRequest.getEventDate().atStartOfDay(), newClaimRequest.getMedicalInstitutionCode(), claimPositions);
    }
}
