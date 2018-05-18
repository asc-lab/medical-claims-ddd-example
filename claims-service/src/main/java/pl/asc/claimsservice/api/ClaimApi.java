package pl.asc.claimsservice.api;

import de.triology.cb.CommandBus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.asc.claimsservice.commands.SubmitClaimCommand;
import pl.asc.claimsservice.commands.SubmitClaimResult;

@RestController
@RequestMapping("/api/claim")
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClaimApi  {
    //private final CommandBus bus;

    //submit
    @GetMapping("/submit")
    public void submit(){
        //SubmitClaimResult submitResult = bus.execute(new SubmitClaimCommand());
    }

    //accept

    //reject

    //get

    //search
}
