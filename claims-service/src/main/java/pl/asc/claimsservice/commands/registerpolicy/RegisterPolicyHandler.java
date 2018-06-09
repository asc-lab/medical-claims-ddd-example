package pl.asc.claimsservice.commands.registerpolicy;

import de.triology.cb.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.asc.claimsservice.domain.Policy;
import pl.asc.claimsservice.domain.PolicyFactory;
import pl.asc.claimsservice.domain.PolicyRepository;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class RegisterPolicyHandler implements CommandHandler<RegisterPolicyResult, RegisterPolicyCommand> {
    private PolicyRepository policyRepository;
    private PolicyFactory policyFactory;

    @Override
    public RegisterPolicyResult handle(RegisterPolicyCommand registerPolicyCommand) {
        //look up policy
        Optional<Policy> policy = policyRepository.findByNumber(registerPolicyCommand.getPolicyVersion().getPolicyNumber());

        if (policy.isPresent()) {
            //if found add version
            policyFactory.createVersion(registerPolicyCommand.getPolicyVersion(), policy.get());
        } else {
            //else construct with version
            Policy newPolicy = policyFactory.create(registerPolicyCommand.getPolicyVersion());
            policyRepository.save(newPolicy);
        }

        return new RegisterPolicyResult();
    }
}
