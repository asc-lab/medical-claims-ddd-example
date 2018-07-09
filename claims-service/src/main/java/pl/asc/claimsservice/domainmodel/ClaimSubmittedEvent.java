package pl.asc.claimsservice.domainmodel;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ClaimSubmittedEvent extends ApplicationEvent {
    private Claim claim;

    public ClaimSubmittedEvent(Object source, Claim claim) {
        super(source);
        this.claim = claim;
    }
}
