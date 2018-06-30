package pl.asc.claimsservice.commands.submitclaim;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.asc.claimsservice.domainmodel.*;
import pl.asc.claimsservice.shared.cqs.CommandHandler;
import pl.asc.claimsservice.shared.exceptions.BusinessException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SubmitClaimHandler implements CommandHandler<SubmitClaimResult, SubmitClaimCommand> {
    private final PolicyRepository policyRepository;
    private final ClaimRepository claimRepository;
    private final LimitConsumptionContainerRepository consumptionContainerRepository;
    private final ClaimNumberGenerator claimNumberGenerator;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public SubmitClaimResult handle(SubmitClaimCommand submitClaimCommand) {
        Optional<Policy> policy = policyRepository.findByNumber(submitClaimCommand.getPolicyNumber());

        if (!policy.isPresent()) {
            throw new BusinessException("POLICY NOT FOUND");
        }

        LimitConsumptionContainerCollection consumptions = consumptionContainerRepository.findForPolicyAndServices(
                submitClaimCommand.getPolicyNumber(),
                collectServiceCodes(submitClaimCommand.getItems()));

        Claim claim = ClaimFactory
                .forPolicy(policy)
                .withNumber(claimNumberGenerator.generateClaimNumber())
                .withEventDate(submitClaimCommand.getEventDate())
                .withItems(submitClaimCommand.getItems())
                .create();

        claim.evaluate(new ClaimEvaluationPolicy(policy.get(), consumptions));

        claimRepository.save(claim);

        consumptionContainerRepository.save(consumptions);

        eventPublisher.publishEvent(new ClaimCreatedEvent(this, claim));

        return SubmitClaimResult.success(claim);
    }

    private List<String> collectServiceCodes(Set<SubmitClaimCommand.Item> items) {
        return items.stream().map(i -> i.getServiceCode()).collect(Collectors.toList());
    }
}
