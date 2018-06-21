package pl.asc.claimsservice.api.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.asc.claimsservice.commands.submitclaim.SubmitClaimCommand;
import pl.asc.claimsservice.commands.submitclaim.SubmitClaimResult;
import pl.asc.claimsservice.queries.findclaim.FindClaimQuery;
import pl.asc.claimsservice.queries.findclaim.FindClaimQueryResult;
import pl.asc.claimsservice.queries.getclaim.GetClaimQuery;
import pl.asc.claimsservice.queries.getclaim.GetClaimResult;
import pl.asc.claimsservice.shared.cqs.CommandBus;

@RestController
@RequestMapping("/api/claim")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClaimApi  {
    private final CommandBus bus;

    //submit
    @PostMapping("/submit")
    public SubmitClaimResult submit(@RequestBody SubmitClaimCommand cmd){
        SubmitClaimResult submitResult = bus.executeCommand(cmd);
        return submitResult;
    }

    //accept

    //reject

    //get
    @GetMapping("/{claimNumber}")
    public GetClaimResult get(@PathVariable(name = "claimNumber") String claimNumber) {
        GetClaimResult getResult = bus.executeQuery(new GetClaimQuery(claimNumber));
        return getResult;
    }

    //search
    @GetMapping("/search")
    public FindClaimQueryResult find() {
        return bus.executeQuery(new FindClaimQuery());
    }
}
