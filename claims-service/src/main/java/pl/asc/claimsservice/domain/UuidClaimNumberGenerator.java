package pl.asc.claimsservice.domain;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidClaimNumberGenerator implements ClaimNumberGenerator {
    @Override
    public String generateClaimNumber() {
        return UUID.randomUUID().toString();
    }
}
