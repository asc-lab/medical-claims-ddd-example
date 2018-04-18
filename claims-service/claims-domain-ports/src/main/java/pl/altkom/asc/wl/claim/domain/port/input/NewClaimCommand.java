package pl.altkom.asc.wl.claim.domain.port.input;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author tdorosz
 */
@Value
public class NewClaimCommand {
    private String policyNumber;
    private LocalDateTime eventDateTime;
    private String medicalInstitutionCode;
    private List<NewClaimPositionDto> claimPositions;
}
