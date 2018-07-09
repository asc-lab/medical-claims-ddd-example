package pl.asc.claimsservice.readmodel;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.asc.claimsservice.domainmodel.Claim;
import pl.asc.claimsservice.domainmodel.ClaimAcceptedEvent;
import pl.asc.claimsservice.domainmodel.ClaimRejectedEvent;
import pl.asc.claimsservice.domainmodel.ClaimSubmittedEvent;

@Service
@Transactional
@RequiredArgsConstructor
public class ClaimViewProjectionService {

    private final ClaimViewRepository claimViewRepository;

    @EventListener
    void onClaimSubmitted(ClaimSubmittedEvent event) {
        saveMappedClaim(event.getClaim());
    }

    @EventListener
    void onClaimAccepted(ClaimAcceptedEvent event) {
        saveMappedClaim(event.getClaim());
    }

    @EventListener
    void onClaimRejected(ClaimRejectedEvent event) {
        saveMappedClaim(event.getClaim());
    }

    private void saveMappedClaim(Claim claim) {
        ClaimView claimView = ClaimViewAssembler.map(claim);
        claimViewRepository.save(claimView);
    }
}
