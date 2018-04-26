package pl.altkom.asc.wl.claim.app.infra.bus;

import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding({Claims.class})
class BusConfiguration {
}
