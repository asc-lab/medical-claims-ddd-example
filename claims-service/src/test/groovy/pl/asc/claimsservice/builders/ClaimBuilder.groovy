package pl.asc.claimsservice.builders

import pl.asc.claimsservice.commands.submitclaim.SubmitClaimCommand
import pl.asc.claimsservice.domainmodel.Claim
import pl.asc.claimsservice.domainmodel.ClaimFactory
import pl.asc.claimsservice.domainmodel.Policy

import java.time.LocalDate

class ClaimBuilder {
    private PolicyBuilder policyBuilder = new PolicyBuilder()

    Claim build(
            String claimNum,
            LocalDate eventDate = LocalDate.of(2018,6,1),
            String serviceCode = "KONS_INTERNISTA",
            BigDecimal qt = 1.0,
            BigDecimal price = 95.0) {
        Policy policy = policyBuilder.build()

        ClaimFactory claimFactory= new ClaimFactory(policy)

        Claim claim = claimFactory
                    .withNumber(claimNum)
                    .withEventDate(eventDate)
                    .withItems([ new SubmitClaimCommand.Item(serviceCode, qt, price) ] as Set)
                    .create()

        claim.evaluate()

        return claim
    }
}
