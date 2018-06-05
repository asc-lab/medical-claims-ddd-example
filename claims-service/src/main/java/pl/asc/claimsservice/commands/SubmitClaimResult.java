package pl.asc.claimsservice.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.asc.claimsservice.domain.Claim;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubmitClaimResult {
    private String claimNumber;

    public static SubmitClaimResult success(Claim claim) {
        return new SubmitClaimResult(claim.getNumber());
    }
}
