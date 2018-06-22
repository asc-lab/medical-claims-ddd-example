package pl.asc.claimsservice.commands

import pl.asc.claimsservice.builders.PolicyBuilder
import pl.asc.claimsservice.commands.registerpolicy.RegisterPolicyCommand
import pl.asc.claimsservice.commands.registerpolicy.RegisterPolicyHandler
import pl.asc.claimsservice.domainmodel.Claim
import pl.asc.claimsservice.domainmodel.Policy
import pl.asc.claimsservice.domainmodel.PolicyRepository
import pl.asc.claimsservice.shared.exceptions.BusinessException
import spock.lang.Specification

class RegisterPolicyHandlerSpecification extends Specification {
    PolicyBuilder policyBuilder = new PolicyBuilder()
    PolicyRepository policyRepository = Stub(PolicyRepository)

    RegisterPolicyHandler registerPolicyHandler = new RegisterPolicyHandler(policyRepository)

    void "can register new policy"() {
        given: "command to register new policy P1"
        def inMemoDb = new HashMap<String, Claim>()
        def cmd = new RegisterPolicyCommand(policyBuilder.buildDto("P1",1))

        and: "policy does not exists"
        policyRepository.findByNumber(_ as String) >> Optional.empty()

        and:
        policyRepository.save(_ as Policy) >> { Policy p ->  inMemoDb.put(p.number, p) }

        when: "handler is called"
        def registrationResult = registerPolicyHandler.handle(cmd)

        then:
        registrationResult != null
        inMemoDb["P1"] != null
        inMemoDb["P1"].number == "P1"
        inMemoDb["P1"].versions.size() == 1

    }

    void "new version is added to existing policy"(){
        given: "command to register version 3 of policy P1212121"
        def cmd = new RegisterPolicyCommand(policyBuilder.buildDto("P1212121",3))

        and: "policy exists with version 1 and 2"
        Policy existingPolicy = policyBuilder.build()
        policyRepository.findByNumber("P1212121") >> Optional.of(existingPolicy)

        when: "handler is called"
        def registrationResult = registerPolicyHandler.handle(cmd)

        then: "third version is created"
        registrationResult != null
        existingPolicy.number == "P1212121"
        existingPolicy.versions.size() == 3

    }

    void "when version already exists exception is thrown"(){
        given: "command to register version 2 of policy P1212121"
        def cmd = new RegisterPolicyCommand(policyBuilder.buildDto("P1212121",2))

        and: "policy exists with versions 1 and 2"
        Policy existingPolicy = policyBuilder.build()
        policyRepository.findByNumber("P1212121") >> Optional.of(existingPolicy)

        when: "handler is called"
        registerPolicyHandler.handle(cmd)

        then: "exception is thrown"
        BusinessException businessEx = thrown()
        businessEx.code == "POLVEREXISTS"
        businessEx.args[0] == "P1212121"
        businessEx.args[1] == 2

    }
}

