package pl.asc.claimsservice.commands

import pl.asc.claimsservice.builders.ClaimBuilder
import pl.asc.claimsservice.builders.PolicyBuilder
import pl.asc.claimsservice.commands.acceptclaim.AcceptClaimCommand
import pl.asc.claimsservice.commands.acceptclaim.AcceptClaimHandler
import pl.asc.claimsservice.domainmodel.Claim
import pl.asc.claimsservice.domainmodel.ClaimRepository
import pl.asc.claimsservice.domainmodel.ClaimStatus
import pl.asc.claimsservice.domainmodel.LimitConsumptionContainerCollection
import pl.asc.claimsservice.domainmodel.Policy
import pl.asc.claimsservice.domainmodel.PolicyAsserts
import spock.lang.Specification

import java.time.LocalDate

class AcceptClaimHandlerSpec extends Specification {
    ClaimRepository claimRepository = Stub(ClaimRepository)
    ClaimBuilder claimBuilder = new ClaimBuilder()
    PolicyBuilder policyBuilder = new PolicyBuilder()
    AcceptClaimHandler acceptClaimHandler = new AcceptClaimHandler(claimRepository)

    void "can accept evaluated claim"() {
        given: "claim 11111 with service KONS_INTERNISTA in status equal evaluated"
        Policy policy = policyBuilder.build()
        LimitConsumptionContainerCollection consumptions = new LimitConsumptionContainerCollection([])
        Claim claim = claimBuilder.build(
                policy,
                consumptions,
                "11111",
                LocalDate.of(2018,6,30),
                "KONS_INTERNISTA")
        claimRepository.getByNumber("11111") >> claim

        when: "acceptance is requested"
        acceptClaimHandler.handle(new AcceptClaimCommand("11111"))

        then: "claim status is equal to accepted"
        claim.status == ClaimStatus.ACCEPTED
        claim.evaluation.paidByInsurer.amount == 71.25

        then: "and no consumptions for service KONS_INTERNISTA"
        PolicyAsserts.of(policy, consumptions).hasConsumptionForService("KONS_INTERNISTA", 95.0)

    }


    void "cannot accept accepted claim"() {
        given: "claim 11111 with service KONS_INTERNISTA in status equal accepted"
        Policy policy = policyBuilder.build()
        Claim claim = claimBuilder.build(
                policy,
                new LimitConsumptionContainerCollection([]),
                "11111",
                LocalDate.of(2018,6,30),
                "KONS_INTERNISTA")
        claim.accept()
        claimRepository.getByNumber("11111") >> claim

        when: "acceptance is requested"
        acceptClaimHandler.handle(new AcceptClaimCommand("11111"))

        then: "IllegalStateException is throw"
        IllegalStateException ex = thrown()
        ex != null

    }

    void "cannot accept rejected claim"() {
        given: "claim 11111 with service KONS_INTERNISTA in status equal rejected"
        Policy policy = policyBuilder.build()
        Claim claim = claimBuilder.build(
                policy,
                new LimitConsumptionContainerCollection([]),
                "11111",
                LocalDate.of(2018,6,30),
                "KONS_INTERNISTA")
        claim.reject()
        claimRepository.getByNumber("11111") >> claim

        when: "acceptance is requested"
        acceptClaimHandler.handle(new AcceptClaimCommand("11111"))

        then: "IllegalStateException is throw"
        IllegalStateException ex = thrown()
        ex != null

    }
}
