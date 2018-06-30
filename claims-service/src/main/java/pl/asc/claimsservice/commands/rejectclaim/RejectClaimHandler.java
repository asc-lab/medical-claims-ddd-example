package pl.asc.claimsservice.commands.rejectclaim;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.asc.claimsservice.domainmodel.*;
import pl.asc.claimsservice.shared.cqs.CommandHandler;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RejectClaimHandler implements CommandHandler<RejectClaimResult, RejectClaimCommand> {
    private final ClaimRepository claimRepository;
    private final LimitConsumptionContainerRepository consumptionContainerRepository;

    @Override
    public RejectClaimResult handle(RejectClaimCommand command) {
        Claim claim = claimRepository.getByNumber(command.getClaimNumber());

        LimitConsumptionContainerCollection consumptions = consumptionContainerRepository.findForPolicyAndServices(
                claim.getPolicyVersionRef().getPolicyNumber(),
                collectServiceCodes(claim));

        claim.reject();

        consumptions.releaseConsumption(claim);

        return new RejectClaimResult();
    }

    private List<String> collectServiceCodes(Claim claim) {
        return claim.getItems().stream().map(i -> i.getServiceCode().getCode()).collect(Collectors.toList());
    }
}
