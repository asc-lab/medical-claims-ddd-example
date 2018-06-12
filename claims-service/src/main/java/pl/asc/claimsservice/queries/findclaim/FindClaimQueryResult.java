package pl.asc.claimsservice.queries.findclaim;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.asc.claimsservice.queries.findclaim.dto.ClaimViewDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FindClaimQueryResult {
    private List<ClaimViewDto> claims;
}
