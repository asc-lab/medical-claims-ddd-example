package pl.altkom.asc.wl.claim.domain

import com.google.common.collect.Lists
import pl.altkom.asc.wl.claim.domain.port.input.NewClaimCommand
import pl.altkom.asc.wl.claim.domain.port.output.PolicyRepository
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

/**
 * @author tdorosz
 */
class SubmitClaimServiceTest extends Specification {

    def newClaimCommand = new NewClaimCommand("POL_1", LocalDateTime.parse("2017-01-01T10:00:00"), "TEST", Lists.newArrayList())

    @Unroll
    def "should return error if policy not exists"() {
        given:
            def retrievePolicyPort = Mock(PolicyRepository.class)
            when(retrievePolicyPort).lastVersion(any(String.class)).thenReturn(Optional.empty())
            def submitClaimService = new ClaimsDomainConfiguration().submitClaimService(retrievePolicyPort)

        when:
            def process = submitClaimService.process(newClaimCommand)

        then:
            true
    }
}
