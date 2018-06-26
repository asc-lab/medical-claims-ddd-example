package pl.asc.claimsservice.commands.acceptclaim;

import lombok.*;
import pl.asc.claimsservice.shared.cqs.Command;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcceptClaimCommand implements Command<AcceptClaimResult> {
    private String claimNumber;
}
