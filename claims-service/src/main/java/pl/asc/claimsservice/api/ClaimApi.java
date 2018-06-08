package pl.asc.claimsservice.api;

import de.triology.cb.CommandBus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/claim")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClaimApi  {
    private final CommandBus bus;

    //submit
    @PostMapping("/submit")
    public void submit(){
        //SubmitClaimResult submitResult = bus.execute(new SubmitClaimCommand());
    }

    //accept

    //reject

    //get

    //search
}
