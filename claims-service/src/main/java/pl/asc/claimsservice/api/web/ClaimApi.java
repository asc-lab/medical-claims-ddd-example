package pl.asc.claimsservice.api.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.asc.claimsservice.commands.acceptclaim.AcceptClaimCommand;
import pl.asc.claimsservice.commands.acceptclaim.AcceptClaimResult;
import pl.asc.claimsservice.commands.rejectclaim.RejectClaimCommand;
import pl.asc.claimsservice.commands.rejectclaim.RejectClaimResult;
import pl.asc.claimsservice.commands.submitclaim.SubmitClaimCommand;
import pl.asc.claimsservice.commands.submitclaim.SubmitClaimResult;
import pl.asc.claimsservice.queries.findclaim.FindClaimQuery;
import pl.asc.claimsservice.queries.findclaim.FindClaimQueryResult;
import pl.asc.claimsservice.queries.getclaim.GetClaimQuery;
import pl.asc.claimsservice.queries.getclaim.GetClaimResult;
import pl.asc.claimsservice.shared.cqs.CommandBus;

@RestController
@RequestMapping("/api/claim")
@RequiredArgsConstructor
public class ClaimApi {

    private final CommandBus bus;

    @PostMapping("/submit")
    public SubmitClaimResult submit(@RequestBody SubmitClaimCommand cmd) {
        return bus.executeCommand(cmd);
    }

    @PostMapping("/accept")
    public AcceptClaimResult accept(@RequestBody AcceptClaimCommand cmd) {
        return bus.executeCommand(cmd);
    }

    @PostMapping("/reject")
    public RejectClaimResult accept(@RequestBody RejectClaimCommand cmd) {
        return bus.executeCommand(cmd);
    }

    @GetMapping("/{claimNumber}")
    public GetClaimResult get(@PathVariable(name = "claimNumber") String claimNumber) {
        return bus.executeQuery(new GetClaimQuery(claimNumber));
    }

    @GetMapping("/search")
    public FindClaimQueryResult find() {
        return bus.executeQuery(new FindClaimQuery());
    }
}
