package pl.asc.claimsservice.commands.submitclaim;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.asc.claimsservice.domainmodel.Claim;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubmitClaimResult {
    private String claimNumber;

    static SubmitClaimResult success(Claim claim) {
        return new SubmitClaimResult(claim.getNumber());
    }
}
