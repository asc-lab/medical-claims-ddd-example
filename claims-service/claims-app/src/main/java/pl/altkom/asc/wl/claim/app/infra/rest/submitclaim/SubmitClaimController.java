package pl.altkom.asc.wl.claim.app.infra.rest.submitclaim;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author tdorosz
 */
@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
class SubmitClaimController {

    private final SubmitClaimAdapter submitClaim;

    @PostMapping(path = "policies/{policyNumber}/claims")
    public void submitNewClaimsForPolicy(@PathVariable("policyNumber") String policyNumber, @RequestBody NewClaimRequestDto newClaimRequest) {
        log.info("Request: {}", newClaimRequest);

        submitClaim.submitNewClaimsForPolicy(policyNumber, newClaimRequest);
    }

}
