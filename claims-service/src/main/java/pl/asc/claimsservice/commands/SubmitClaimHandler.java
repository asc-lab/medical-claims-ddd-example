package pl.asc.claimsservice.commands;

import de.triology.cb.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public class SubmitClaimHandler implements CommandHandler<SubmitClaimResult, SubmitClaimCommand> {
    @Override
    public SubmitClaimResult handle(SubmitClaimCommand submitClaimCommand) {
        return null;
    }
}
