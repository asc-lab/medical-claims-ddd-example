package pl.asc.claimsservice.commands.registerpolicy;

import lombok.*;
import pl.asc.claimsservice.commands.registerpolicy.dto.PolicyVersionDto;
import pl.asc.claimsservice.shared.cqs.Command;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPolicyCommand implements Command<RegisterPolicyResult> {
    @NotNull
    private PolicyVersionDto policyVersion;
}
