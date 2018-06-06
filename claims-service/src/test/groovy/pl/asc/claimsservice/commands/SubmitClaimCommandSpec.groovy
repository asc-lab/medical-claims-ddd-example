package pl.asc.claimsservice.commands

import pl.asc.claimsservice.domain.Claim
import pl.asc.claimsservice.domain.ClaimNumberGenerator
import pl.asc.claimsservice.domain.ClaimRepository
import pl.asc.claimsservice.domain.PolicyBuilder
import pl.asc.claimsservice.domain.PolicyRepository
import pl.asc.claimsservice.domain.UuidClaimNumberGenerator
import spock.lang.Specification

import java.time.LocalDate

class SubmitClaimCommandSpec extends Specification {
    PolicyBuilder policyBuilder = new PolicyBuilder()

    PolicyRepository policyRepository = Stub(PolicyRepository)
    ClaimRepository claimRepository = Stub(ClaimRepository)
    ClaimNumberGenerator claimNumberGenerator = new UuidClaimNumberGenerator()

    def inMemoDb = new HashMap<String, Claim>()

    SubmitClaimHandler submitClaimHandler = new SubmitClaimHandler(
            policyRepository,
            claimRepository,
            claimNumberGenerator
    )

    void "cannot register claim if policy does not exist"() {
        given: "submit claim command"
        def cmd = SubmitClaimCommand.builder()
            .policyNumber("P1212121")
            .eventDate(LocalDate.of(2018,3,1))
            .medicalServiceProviderCode("LUXMED 001")
            .items([
                SubmitClaimCommand.Item.builder().serviceCode("").quantity(1.0).price(20.0).build()
            ] as Set)
            .build()

        and: "policy"
            policyRepository.findByNumber(_ as String) >> Optional.of(policyBuilder.build())
        and:
            claimRepository.save(_ as Claim) >> { Claim c ->  inMemoDb.put(c.number, c) }

        when:
        def submitResult = submitClaimHandler.handle(cmd)

        then:
        submitResult.claimNumber != null
        def claim = inMemoDb.get(submitResult.claimNumber)
        claim != null
        claim.number == submitResult.claimNumber
        claim.eventDate == LocalDate.of(2018,3,1)
        claim.policyVersion.policy.number == "P1212121"
    }
}