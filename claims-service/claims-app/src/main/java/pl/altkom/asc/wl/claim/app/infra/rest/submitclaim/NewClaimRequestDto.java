package pl.altkom.asc.wl.claim.app.infra.rest.submitclaim;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

/**
 * @author tdorosz
 */
@Data
class NewClaimRequestDto {
    private LocalDate eventDate;
    private String medicalInstitutionCode;
    private List<ClaimPositionDto> claimPositions;
}
