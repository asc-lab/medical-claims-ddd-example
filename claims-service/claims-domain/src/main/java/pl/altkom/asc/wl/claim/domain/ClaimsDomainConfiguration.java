package pl.altkom.asc.wl.claim.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.altkom.asc.wl.claim.domain.port.input.GetPolicyPort;
import pl.altkom.asc.wl.claim.domain.port.output.ClaimRepository;
import pl.altkom.asc.wl.claim.domain.port.output.PolicyRepository;

@Configuration
class ClaimsDomainConfiguration {

    @Bean
    GetPolicyPort getPolicyPort(PolicyRepository retrievePolicyPort) {
        return new PolicyService(retrievePolicyPort);
    }

    @Bean
    SubmitClaimService submitClaimService(PolicyRepository retrievePolicyPort, ClaimRepository claimRepository) {
        return new SubmitClaimService(retrievePolicyPort, claimRepository);
    }
}
