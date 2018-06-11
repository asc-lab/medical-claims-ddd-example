package pl.asc.claimsservice.domainmodel;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidClaimNumberGenerator implements ClaimNumberGenerator {
    @Override
    public String generateClaimNumber() {
        return UUID.randomUUID().toString();
    }
}
