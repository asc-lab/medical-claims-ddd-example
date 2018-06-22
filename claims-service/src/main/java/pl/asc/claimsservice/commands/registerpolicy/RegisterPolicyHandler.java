package pl.asc.claimsservice.commands.registerpolicy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.asc.claimsservice.domainmodel.Policy;
import pl.asc.claimsservice.domainmodel.PolicyFactory;
import pl.asc.claimsservice.domainmodel.PolicyRepository;
import pl.asc.claimsservice.shared.cqs.CommandHandler;
import pl.asc.claimsservice.shared.exceptions.BusinessException;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class RegisterPolicyHandler implements CommandHandler<RegisterPolicyResult, RegisterPolicyCommand> {
    private final PolicyRepository policyRepository;
    private final PolicyFactory policyFactory = new PolicyFactory();

    @Override
    public RegisterPolicyResult handle(RegisterPolicyCommand registerPolicyCommand) {
        Optional<Policy> policy = policyRepository.findByNumber(registerPolicyCommand.getPolicyVersion().getPolicyNumber());

        if (policy.isPresent()) {
            addNewVersionToExistingPolicy(registerPolicyCommand, policy.get());

        } else {
            createNewPolicyWithFirstVersion(registerPolicyCommand);

        }

        return new RegisterPolicyResult();
    }

    private void createNewPolicyWithFirstVersion(RegisterPolicyCommand registerPolicyCommand) {
        Policy newPolicy = policyFactory.create(registerPolicyCommand.getPolicyVersion());
        policyRepository.save(newPolicy);
    }

    private void addNewVersionToExistingPolicy(RegisterPolicyCommand registerPolicyCommand, Policy policy) {
        policyFactory.createVersion(registerPolicyCommand.getPolicyVersion(), policy);
    }
}
