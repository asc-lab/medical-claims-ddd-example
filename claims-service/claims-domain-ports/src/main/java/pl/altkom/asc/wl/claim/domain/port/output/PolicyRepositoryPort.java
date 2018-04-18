package pl.altkom.asc.wl.claim.domain.port.output;

import java.util.Optional;

/**
 * @author tdorosz
 */
public interface PolicyRepositoryPort {
    Optional<PolicyFromStorageDto> get(String policyNumber);

    boolean exists(String policyNumber);
}
