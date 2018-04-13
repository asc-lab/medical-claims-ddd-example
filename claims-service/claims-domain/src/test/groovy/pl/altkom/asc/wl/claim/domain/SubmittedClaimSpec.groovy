package pl.altkom.asc.wl.claim.domain

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.LocalDateTime

import static java.time.LocalDate.now

class SubmittedClaimSpec extends Specification {

    @Unroll
    def "submitted claim is out of policy coverage range"(LocalDateTime incidentTime, LocalDate validFrom, LocalDate validTo) {
        given:
          def policy = new Policy('policyNumber', [new PolicyVersion(1, validFrom, validTo)])
        when:
          def claim = new SubmittedClaim(policy, LocalDateTime.now())
        then:
          claim.isRejected();
        where:
          incidentTime        | validFrom            | validTo
          LocalDateTime.now() | now().minusMonths(2) | now().minusMonths(1)
          LocalDateTime.now() | now().minusWeeks(2)  | now().minusDays(1)
    }
}
