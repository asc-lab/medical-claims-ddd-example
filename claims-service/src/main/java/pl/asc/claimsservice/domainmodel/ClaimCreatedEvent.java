package pl.asc.claimsservice.domainmodel;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ClaimCreatedEvent extends ApplicationEvent {
    private Claim claim;

    public ClaimCreatedEvent(Object source, Claim claim) {
        super(source);
        this.claim = claim;
    }
}
