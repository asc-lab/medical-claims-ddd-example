package pl.altkom.asc.wl.claim.domain

import pl.altkom.asc.wl.claim.domain.port.output.PolicyRepositoryPort
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author tdorosz
 */
class PolicyServiceTest extends Specification {
    @Unroll
    def "policy should be retrieved"(String policyNumber) {
        given:
        def policyRetriever =  Mock(PolicyRepositoryPort)
        def PolicyService defaultGetPolicyPort = new PolicyService(policyRetriever);
        when:
        def result = defaultGetPolicyPort.get(policyNumber)
        then:
        result.isPresent()
        where:
        policyNumber << ["1", "2", "3"]

    }
}
