package pl.asc.claimsservice.adapters;

import org.springframework.stereotype.Component;
import pl.asc.claimsservice.domain.ClaimNumberGenerator;

import java.util.UUID;

@Component
public class UuidClaimNumberGenerator implements ClaimNumberGenerator {
    @Override
    public String generateClaimNumber() {
        return UUID.randomUUID().toString();
    }
}
