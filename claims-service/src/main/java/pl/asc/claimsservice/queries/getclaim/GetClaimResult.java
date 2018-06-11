package pl.asc.claimsservice.queries.getclaim;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.asc.claimsservice.queries.getclaim.dto.ClaimDto;

@Getter
@Setter
@Builder
public class GetClaimResult {
    private ClaimDto claim;
}
