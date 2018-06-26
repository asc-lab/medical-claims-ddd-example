package pl.asc.claimsservice.commands.acceptclaim;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.asc.claimsservice.domainmodel.Claim;
import pl.asc.claimsservice.domainmodel.ClaimRepository;
import pl.asc.claimsservice.shared.cqs.CommandHandler;

@Service
@Transactional
@RequiredArgsConstructor
public class AcceptClaimHandler implements CommandHandler<AcceptClaimResult, AcceptClaimCommand> {
    private final ClaimRepository claimRepository;

    @Override
    public AcceptClaimResult handle(AcceptClaimCommand command) {
        Claim claim = claimRepository.getByNumber(command.getClaimNumber());

        claim.accept();

        return new AcceptClaimResult();
    }
}
