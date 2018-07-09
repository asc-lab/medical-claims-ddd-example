package pl.asc.claimsservice.domainmodel

import spock.lang.Specification

class ServiceCodeSpec extends Specification {

    void "services with the same code are equal"() {
        given:
        ServiceCode a = ServiceCode.of("S")
        ServiceCode b = ServiceCode.of("S")

        expect:
        a == b
        b == a
    }
}
