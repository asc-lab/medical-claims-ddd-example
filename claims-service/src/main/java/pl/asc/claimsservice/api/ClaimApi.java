package pl.asc.claimsservice.api;

import de.triology.cb.CommandBus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.asc.claimsservice.commands.submitclaim.SubmitClaimCommand;
import pl.asc.claimsservice.commands.submitclaim.SubmitClaimResult;

@RestController
@RequestMapping("/api/claim")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClaimApi  {
    private final CommandBus bus;

    //submit
    @PostMapping("/submit")
    public SubmitClaimResult submit(@RequestBody SubmitClaimCommand cmd){
        SubmitClaimResult submitResult = bus.execute(cmd);
        return submitResult;
    }

    //accept

    //reject

    //get

    //search
}
