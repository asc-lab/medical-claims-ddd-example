package pl.asc.claimsservice.domainmodel;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ClaimRejectedEvent extends ApplicationEvent {
    private Claim claim;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ClaimRejectedEvent(Object source, Claim claim) {
        super(source);
        this.claim = claim;
    }
}