package pl.altkom.asc.wl.claim.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.altkom.asc.wl.claim.domain.port.input.GetPolicyPort;
import pl.altkom.asc.wl.claim.domain.port.output.PolicyRepositoryPort;

@Configuration
class ClaimsDomainConfiguration {

    @Bean
    GetPolicyPort getPolicyPort(PolicyRepositoryPort retrievePolicyPort) {
        return new PolicyService(retrievePolicyPort);
    }

    @Bean
    SubmitClaimService submitClaimService(PolicyRepositoryPort retrievePolicyPort) {
        return new SubmitClaimService(retrievePolicyPort);
    }
}
