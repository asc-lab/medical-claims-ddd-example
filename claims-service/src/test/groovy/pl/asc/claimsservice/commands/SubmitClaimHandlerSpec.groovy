package pl.asc.claimsservice.commands

import org.springframework.context.ApplicationEventPublisher
import pl.asc.claimsservice.commands.submitclaim.SubmitClaimCommand
import pl.asc.claimsservice.commands.submitclaim.SubmitClaimHandler
import pl.asc.claimsservice.domainmodel.Claim
import pl.asc.claimsservice.domainmodel.ClaimNumberGenerator
import pl.asc.claimsservice.domainmodel.ClaimRepository
import pl.asc.claimsservice.builders.PolicyBuilder
import pl.asc.claimsservice.domainmodel.PolicyRepository
import pl.asc.claimsservice.domainmodel.UuidClaimNumberGenerator
import spock.lang.Specification

import java.time.LocalDate

class SubmitClaimHandlerSpec extends Specification {
    PolicyBuilder policyBuilder = new PolicyBuilder()

    PolicyRepository policyRepository = Stub(PolicyRepository)
    ClaimRepository claimRepository = Stub(ClaimRepository)
    ClaimNumberGenerator claimNumberGenerator = new UuidClaimNumberGenerator()
    ApplicationEventPublisher eventPublisher = Stub(ApplicationEventPublisher)

    def inMemoDb = new HashMap<String, Claim>()

    SubmitClaimHandler submitClaimHandler = new SubmitClaimHandler(
            policyRepository,
            claimRepository,
            claimNumberGenerator,
            eventPublisher
    )

    void "can register claim"() {
        given: "submit claim command"
        def cmd = SubmitClaimCommand.builder()
            .policyNumber("P1212121")
            .eventDate(LocalDate.of(2018,3,1))
            .medicalServiceProviderCode("LUXMED 001")
            .items([
                SubmitClaimCommand.Item.builder().serviceCode("KONS_INTERNISTA").quantity(1.0).price(20.0).build()
            ] as Set)
            .build()

        and: "valid policy"
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