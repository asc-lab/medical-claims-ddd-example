package pl.altkom.asc.wl.claim.app.infra.storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.stereotype.Component;

import pl.altkom.asc.wl.claim.domain.event.DomainEvent;

import java.io.IOException;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Component
class EventSerializer {

    private final ObjectMapper objectMapper;

    EventSerializer() {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    EventDescriptor serialize(DomainEvent event) {
        try {
            return new EventDescriptor(objectMapper.writeValueAsString(event), event.when(), event.type(), event.aggregateUuid());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    DomainEvent deserialize(EventDescriptor eventDescriptor) {
        try {
            return objectMapper.readValue(eventDescriptor.getBody(), DomainEvent.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
