package pl.altkom.asc.wl.claim.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import pl.altkom.asc.wl.claim.domain.Incident;
import pl.altkom.asc.wl.claim.domain.policy.PolicyVersion;

import java.time.Instant;
import java.util.UUID;

import lombok.Value;

@Value
public class SubmittedClaimPreliminaryAccepted implements DomainEvent {

    private static final String TYPE = "submitted.claim.preliminary.accepted";

    private UUID uuid;
    private Instant moment;
    private PolicyVersion policyVersion;
    private Incident incident;

    public SubmittedClaimPreliminaryAccepted(
            @JsonProperty UUID uuid,
            @JsonProperty Instant moment,
            @JsonProperty PolicyVersion policyVersion,
            @JsonProperty Incident incident) {
        this.uuid = uuid;
        this.moment = moment;
        this.policyVersion = policyVersion;
        this.incident = incident;
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public Instant when() {
        return moment;
    }

    @Override
    public UUID aggregateUuid() {
        return uuid;
    }
}
