package pl.altkom.asc.wl.claim.app.infra.storage;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Getter;

@Entity(name = "event_descriptors")
class EventDescriptor {

    public enum Status {
        PENDING, SENT
    }

    @Id
    @GeneratedValue(generator = "event_descriptors_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "event_descriptors_seq", sequenceName = "event_descriptors_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 600)
    @Getter
    private String body;

    @Column(nullable = false, name = "occurred_at")
    @Getter
    private Instant occurredAt;

    @Column(nullable = false, length = 60)
    private String type;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Column(nullable = false, name = "aggregate_uuid", length = 36)
    private UUID aggregateUUID;

    EventDescriptor(String body, Instant occurredAt, String type, UUID aggregateUUID) {
        this.body = body;
        this.occurredAt = occurredAt;
        this.type = type;
        this.aggregateUUID = aggregateUUID;
    }

    private EventDescriptor() {
    }

    public EventDescriptor sent() {
        this.status = Status.SENT;
        return this;
    }
}
