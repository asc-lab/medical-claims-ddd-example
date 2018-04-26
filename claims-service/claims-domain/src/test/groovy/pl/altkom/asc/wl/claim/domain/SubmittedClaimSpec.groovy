package pl.altkom.asc.wl.claim.domain

import pl.altkom.asc.wl.claim.domain.policy.*
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.LocalDateTime

import static java.math.BigDecimal.ZERO
import static java.time.LocalDate.now
import static pl.altkom.asc.wl.claim.domain.policy.CoverLimit.builder
import static pl.altkom.asc.wl.claim.domain.policy.LimitPeriod.POLICY_YEAR

class SubmittedClaimSpec extends Specification {

    @Unroll
    def "submitted claim is out of policy coverage range #validFrom to #validTo"(LocalDateTime incidentTime, LocalDate validFrom, LocalDate validTo) {
        given:
          def policy = new PolicyVersion('policyNumber', 1, validFrom, validTo, [] as Set)
        when:
          def claim = new SubmittedClaim(UUID.randomUUID())
          claim.submitted(policy, new Incident(LocalDateTime.now(), "code", [] as Set))
        then:
          claim.isRejected()
        where:
          incidentTime        | validFrom            | validTo
          LocalDateTime.now() | now().minusMonths(2) | now().minusMonths(1)
          LocalDateTime.now() | now().minusWeeks(2)  | now().minusDays(1)
    }

    @Unroll
    def "submitted claim preliminary acceptance for #items"(List<ClaimItem> items, BigDecimal customerCommitment, BigDecimal companyCommitment) {
        given:
          def now = now()
          def policyVersion = this.policyVersion(now)
          def incident = new Incident(LocalDateTime.now(), 'code', items as Set)
        when:
          def claim = new SubmittedClaim(UUID.randomUUID())
          claim.submitted(policyVersion, incident)
        then:
          claim.currentCommitment() == new Commitment(companyCommitment, customerCommitment)
        where:
          items                                                       | customerCommitment        | companyCommitment
          [new ClaimItem('unknownCode', 1, new BigDecimal('1000'))]   | new BigDecimal('1000')    | ZERO
          [
                  new ClaimItem('unknownCode1', 1, new BigDecimal('1000')),
                  new ClaimItem('unknownCode2', 2, new BigDecimal('10')),
                  new ClaimItem('unknownCode3', 1, new BigDecimal('13.58'))
          ]                                                           | new BigDecimal('1033.58') | ZERO
          [new ClaimItem('KONS_INTERNISTA', 1, new BigDecimal('89'))] | new BigDecimal('22.25')   | new BigDecimal('66.75')
          [
                  new ClaimItem('KONS_INTERNISTA', 1, new BigDecimal('89')),
                  new ClaimItem('unknownCode1', 1, new BigDecimal('1000'))
          ]                                                           | new BigDecimal('1022.25') | new BigDecimal('66.75')
          [
                  new ClaimItem('KONS_PEDIATRA', 1, new BigDecimal('112.56')),
                  new ClaimItem('LAB_KREW_OB', 2, new BigDecimal('12.38'))
          ]                                                           | new BigDecimal('12.48')   | new BigDecimal('124.84')
    }

    def policyVersion(LocalDate now) {
        new PolicyVersion('policyNumber', 1, now.minusMonths(1), now.plusMonths(1), [
                new Cover('KONS', [
                        new CoverItem('KONS_INTERNISTA', new PercentageCoPayment(25), builder().maxAmount(100).period(POLICY_YEAR).build()),
                        new CoverItem('KONS_PEDIATRA', new AmountCoPayment(new BigDecimal('10')), builder().maxQty(20).maxAmount(100).period(POLICY_YEAR).build())
                ] as Set),
                new Cover('LAB', [
                        new CoverItem('LAB_KREW_OB', new PercentageCoPayment(10), builder().maxQty(5).maxAmount(50).period(POLICY_YEAR).build()),
                        new CoverItem('LAB_HDL', new AmountCoPayment(new BigDecimal('2')), builder().maxQty(2).maxAmount(28).period(POLICY_YEAR).build())
                ] as Set)
        ] as Set)
    }
}
