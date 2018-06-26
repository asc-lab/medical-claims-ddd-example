package pl.asc.claimsservice.commands

import pl.asc.claimsservice.builders.ClaimBuilder
import pl.asc.claimsservice.commands.rejectclaim.RejectClaimCommand
import pl.asc.claimsservice.commands.rejectclaim.RejectClaimHandler
import pl.asc.claimsservice.domainmodel.Claim
import pl.asc.claimsservice.domainmodel.ClaimRepository
import pl.asc.claimsservice.domainmodel.ClaimStatus
import pl.asc.claimsservice.domainmodel.PolicyAsserts
import spock.lang.Specification

import java.time.LocalDate

class RejectClaimHandlerSpec extends Specification {
    ClaimRepository claimRepository = Stub(ClaimRepository)
    ClaimBuilder claimBuilder = new ClaimBuilder()
    RejectClaimHandler rejectClaimHandler = new RejectClaimHandler(claimRepository)

    void "can reject evaluated claim"() {
        given: "claim 11111 with service KONS_INTERNISTA in status equal evaluated"
        Claim claim = claimBuilder.build(
                "11111",
                LocalDate.of(2018,6,30),
                "KONS_INTERNISTA")
        claimRepository.getByNumber("11111") >> claim

        when: "rejection is requested"
        rejectClaimHandler.handle(new RejectClaimCommand("11111"))

        then: "claim status is equal to rejected"
        claim.status == ClaimStatus.REJECTED
        claim.evaluation.paidByInsurer.amount == 0.00

        then: "and no consumptions for service KONS_INTERNISTA"
        PolicyAsserts.of(claim.policyVersion.policy).hasNoConsumptionForService("KONS_INTERNISTA")

    }


    void "cannot reject accepted claim"() {
        given: "claim 11111 with service KONS_INTERNISTA in status equal accepted"
        Claim claim = claimBuilder.build(
                "11111",
                LocalDate.of(2018,6,30),
                "KONS_INTERNISTA")
        claim.accept()
        claimRepository.getByNumber("11111") >> claim

        when: "rejection is requested"
        rejectClaimHandler.handle(new RejectClaimCommand("11111"))

        then: "IllegalStateException is throw"
        IllegalStateException ex = thrown()
        ex != null

    }

    void "cannot reject rejected claim"() {
        given: "claim 11111 with service KONS_INTERNISTA in status equal rejected"
        Claim claim = claimBuilder.build(
                "11111",
                LocalDate.of(2018,6,30),
                "KONS_INTERNISTA")
        claim.reject()
        claimRepository.getByNumber("11111") >> claim

        when: "rejection is requested"
        rejectClaimHandler.handle(new RejectClaimCommand("11111"))

        then: "IllegalStateException is throw"
        IllegalStateException ex = thrown()
        ex != null

    }
}
