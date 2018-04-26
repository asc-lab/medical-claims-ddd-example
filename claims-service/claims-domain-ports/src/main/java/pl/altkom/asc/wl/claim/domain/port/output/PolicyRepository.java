package pl.altkom.asc.wl.claim.domain.port.output;

import java.util.Optional;

public interface PolicyRepository {
    Optional<PolicyVersionDto> lastVersion(String policyNumber);
}
