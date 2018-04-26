package pl.altkom.asc.wl.claim.domain.port.output;

import pl.altkom.asc.wl.claim.domain.event.DomainEvent;

import java.util.List;
import java.util.UUID;

public interface ClaimRepository {
    void save(UUID aggregateUuid, List<DomainEvent> events);
}
