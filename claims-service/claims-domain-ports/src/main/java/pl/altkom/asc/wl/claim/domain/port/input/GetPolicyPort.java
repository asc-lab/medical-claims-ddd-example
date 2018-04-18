package pl.altkom.asc.wl.claim.domain.port.input;

import java.util.Optional;

/**
 * @author tdorosz
 */
public interface GetPolicyPort {
    Optional<PolicyDto> get(String policyNumber);
}
