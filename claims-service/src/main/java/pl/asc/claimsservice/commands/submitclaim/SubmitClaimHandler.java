package pl.asc.claimsservice.commands.submitclaim;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.asc.claimsservice.domainmodel.*;
import pl.asc.claimsservice.shared.cqs.CommandHandler;
import pl.asc.claimsservice.shared.exceptions.BusinessException;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SubmitClaimHandler implements CommandHandler<SubmitClaimResult, SubmitClaimCommand> {
    private final PolicyRepository policyRepository;
    private final ClaimRepository claimRepository;
    private final ClaimNumberGenerator claimNumberGenerator;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public SubmitClaimResult handle(SubmitClaimCommand submitClaimCommand) {
        Optional<Policy> policy = policyRepository.findByNumber(submitClaimCommand.getPolicyNumber());

        if (!policy.isPresent()) {
            throw new BusinessException("POLICY NOT FOUND");
        }

        Claim claim = ClaimFactory
                .forPolicy(policy)
                .withNumber(claimNumberGenerator.generateClaimNumber())
                .withEventDate(submitClaimCommand.getEventDate())
                .withItems(submitClaimCommand.getItems())
                .create();

        claim.evaluate();

        claimRepository.save(claim);

        eventPublisher.publishEvent(new ClaimCreatedEvent(this, claim));

        return SubmitClaimResult.success(claim);
    }
}
