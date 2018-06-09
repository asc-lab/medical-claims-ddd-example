package pl.asc.claimsservice.commands.registerpolicy;

import de.triology.cb.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.asc.claimsservice.commands.registerpolicy.dto.PolicyVersionDto;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class RegisterPolicyCommand implements Command<RegisterPolicyResult> {
    @NotNull
    private PolicyVersionDto policyVersion;
}
