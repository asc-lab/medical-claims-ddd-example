package pl.asc.claimsservice.readmodel;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.asc.claimsservice.domainmodel.Claim;
import pl.asc.claimsservice.domainmodel.ClaimCreatedEvent;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClaimViewProjectionService {
    private final ClaimViewRepository claimViewRepository;

    @EventListener
    void onClaimCreated(ClaimCreatedEvent claimCreatedEvent){
        ClaimView claimView = createClaimView(claimCreatedEvent.getClaim());
        claimViewRepository.save(claimView);
    }

    void onClaimApproved() {}

    void onClaimRejected() {}

    private ClaimView createClaimView(Claim claim) {
        return new ClaimView(
                null,
                claim.getNumber(),
                claim.getStatus().toString(),
                claim.getPolicyVersion().getPolicy().getNumber(),
                claim.getEventDate(),
                claim.getEvaluation().getPaidByCustomer().add(claim.getEvaluation().getPaidByInsurer()).getAmount(),
                claim.getEvaluation().getPaidByInsurer().getAmount(),
                claim.getEvaluation().getPaidByCustomer().getAmount(),
                claim.getItems().stream().map(s->s.getServiceCode()).collect(Collectors.toList())
        );
    }
}
