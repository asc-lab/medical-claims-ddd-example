package pl.asc.claimsservice.domainmodel

import spock.lang.Specification

import java.time.LocalDate

class PolicyVersionCollectionSpec extends Specification {

    void "can find version by number"() {
        given:
        def versions = versions()

        when:
        def v1 = versions.withNumber(1)
        def v2 = versions.withNumber(2)

        then:
        v1.versionNumber == 1
        v2.versionNumber == 2
    }


    void "can find version at date"(){
        given:
        def versions = versions()

        when:
        def verOnJanuary = versions.validAtDate(LocalDate.of(2018,1,1))
        def verOnJuly = versions.validAtDate(LocalDate.of(2018,7,1))
        def verOnDecember = versions.validAtDate(LocalDate.of(2018,12,31))
        def verBeforeStart = versions.validAtDate(LocalDate.of(2017,12,31))
        def verAfterEnd = versions.validAtDate(LocalDate.of(2020,1,1))


        then:
        verOnJanuary.versionNumber == 1
        verOnJuly.versionNumber == 2
        verOnDecember.versionNumber == 2
        verBeforeStart.versionNumber == 2
        verAfterEnd.versionNumber == 2
    }

    private PolicyVersionCollection versions(){
        PolicyBuilder builder = new PolicyBuilder()
        return builder.build().versions()
    }
}
