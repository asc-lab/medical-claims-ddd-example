package pl.altkom.asc.wl.claim.domain.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import pl.altkom.asc.wl.claim.domain.Incident;
import pl.altkom.asc.wl.claim.domain.policy.PolicyVersion;

import java.time.Instant;
import java.util.UUID;

import lombok.Value;

@Value
public class SubmittedClaimRejected implements DomainEvent {

    private static final String TYPE = "submitted.claim.rejected";

    private UUID uuid;
    private Instant moment;
    private PolicyVersion policyVersion;
    private Incident incident;

    @JsonCreator
    public SubmittedClaimRejected(
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
