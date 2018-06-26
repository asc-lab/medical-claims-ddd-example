package pl.asc.claimsservice.commands.rejectclaim;


import lombok.*;
import pl.asc.claimsservice.shared.cqs.Command;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RejectClaimCommand implements Command<RejectClaimResult> {
    private String claimNumber;
}
