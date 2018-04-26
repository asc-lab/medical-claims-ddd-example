package pl.altkom.asc.wl.claim.app.infra.storage;

import org.springframework.stereotype.Component;

import pl.altkom.asc.wl.claim.domain.event.DomainEvent;
import pl.altkom.asc.wl.claim.domain.port.output.ClaimRepository;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
class EventSourcedClaimsRepository implements ClaimRepository {

    private final EventStore eventStore;
    private final EventSerializer eventSerializer;

    @Override
    public void save(UUID aggregateId, List<DomainEvent> events) {
        eventStore.saveEvents(
                aggregateId,
                events.stream().map(eventSerializer::serialize).collect(toList())
        );
    }
}
