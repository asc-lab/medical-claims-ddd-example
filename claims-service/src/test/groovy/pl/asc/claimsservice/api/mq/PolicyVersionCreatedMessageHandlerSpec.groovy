package pl.asc.claimsservice.api.mq

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.messaging.support.GenericMessage
import pl.asc.claimsservice.ClaimsServiceApplication
import pl.asc.claimsservice.builders.PolicyBuilder
import pl.asc.claimsservice.domainmodel.PolicyRepository
import spock.lang.Specification

@SpringBootTest(classes = ClaimsServiceApplication)
class PolicyVersionCreatedMessageHandlerSpec extends Specification {

    @Autowired
    Sink sink

    PolicyBuilder policyBuilder = new PolicyBuilder()

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    PolicyRepository policyRepository

    void "can receive message and process it"() {
        given: "a message"
        def policyVersion = policyBuilder.buildDto()
        def headers = ["eventType": "policyVersionCreated"]
        def msg = new GenericMessage(objectMapper.writeValueAsString(policyVersion), headers)

        when: "message is send"
        sink.input().send(msg)

        then: "policyRef gets registered"
        policyRepository.findByNumber("P1").isPresent()
    }
}
