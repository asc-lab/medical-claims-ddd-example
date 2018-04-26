package pl.altkom.asc.wl.claim.app.infra.bus;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Claims {

    @Output
    MessageChannel subscribedClaimEvents();
}
