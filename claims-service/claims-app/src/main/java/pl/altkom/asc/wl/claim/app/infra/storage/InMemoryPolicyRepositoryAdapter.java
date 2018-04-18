package pl.altkom.asc.wl.claim.app.infra.storage;

import org.springframework.stereotype.Component;
import pl.altkom.asc.wl.claim.domain.port.output.PolicyFromStorageDto;
import pl.altkom.asc.wl.claim.domain.port.output.PolicyRepositoryPort;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author tdorosz
 */
@Component
public class InMemoryPolicyRepositoryAdapter implements PolicyRepositoryPort {

    private final Map<String, PolicyFromStorageDto> repository;

    public InMemoryPolicyRepositoryAdapter() {
        this.repository = new HashMap<>();
        this.repository.put("1590N100", new PolicyFromStorageDto());
        this.repository.put("1590N101", new PolicyFromStorageDto());
        this.repository.put("1590N102", new PolicyFromStorageDto());
    }

    @Override
    public Optional<PolicyFromStorageDto> get(String policyNumber) {
        PolicyFromStorageDto policyFromStorageDto = repository.get(policyNumber);

        return Optional.ofNullable(policyFromStorageDto);
    }

    @Override
    public boolean exists(String policyNumber) {
        return this.repository.containsKey(policyNumber);
    }

}
