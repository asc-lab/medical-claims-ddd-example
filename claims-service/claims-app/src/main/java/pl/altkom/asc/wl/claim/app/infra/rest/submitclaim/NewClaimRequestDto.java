package pl.altkom.asc.wl.claim.app.infra.rest.submitclaim;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

/**
 * @author tdorosz
 */
@Getter
@Setter
@ToString
class NewClaimRequestDto {
    private LocalDate eventDate;
    private String medicalInstitutionCode;
    private List<ClaimPositionDto> claimPositions;
}
