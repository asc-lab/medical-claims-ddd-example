package pl.altkom.asc.wl.claim.domain.policy;

import java.util.Optional;
import java.util.Set;

import lombok.Value;

@Value
public class Cover {

    private String code;
    private Set<CoverItem> services;

    public Optional<CoverItem> findService(String code) {
        return services.stream()
                .filter(coverItem -> coverItem.getCode().equals(code))
                .findFirst();
    }
}
