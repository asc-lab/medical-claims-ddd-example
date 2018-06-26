package pl.asc.claimsservice.commands.rejectclaim;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.asc.claimsservice.domainmodel.Claim;
import pl.asc.claimsservice.domainmodel.ClaimRepository;
import pl.asc.claimsservice.shared.cqs.CommandHandler;

@Service
@Transactional
@RequiredArgsConstructor
public class RejectClaimHandler implements CommandHandler<RejectClaimResult, RejectClaimCommand> {
    private final ClaimRepository claimRepository;

    @Override
    public RejectClaimResult handle(RejectClaimCommand command) {
        Claim claim = claimRepository.getByNumber(command.getClaimNumber());

        claim.reject();

        return new RejectClaimResult();
    }
}
