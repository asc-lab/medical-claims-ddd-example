package pl.altkom.asc.wl.claim.app.infra.storage;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EntityScan
@EnableJpaRepositories("pl.altkom.asc.wl.claim.app.infra.storage")
class JpaConfiguration {
}
