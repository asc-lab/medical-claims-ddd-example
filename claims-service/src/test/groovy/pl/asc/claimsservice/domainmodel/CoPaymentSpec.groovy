package pl.asc.claimsservice.domainmodel

import pl.asc.claimsservice.shared.primitives.MonetaryAmount
import pl.asc.claimsservice.shared.primitives.Percent
import spock.lang.Specification

class CoPaymentSpec extends Specification {
    void "given empty co-payment customer pays zero"() {
        given:
        def coPay = CoPayment.empty()
        when:
        def result = coPay.calculate(MonetaryAmount.from(100))
        then:
        result.amount == 0
    }

    void "given 10 percent co-payment customer pays 10"() {
        given:
        def coPay = CoPayment.percent(Percent.of(10))
        when:
        def result = coPay.calculate(MonetaryAmount.from(100))
        then:
        result.amount == 10
    }

    void "given 15 amount co-payment customer pays 15"() {
        given:
        def coPay = CoPayment.amount(MonetaryAmount.from(15))
        when:
        def result = coPay.calculate(MonetaryAmount.from(100))
        then:
        result.amount == 15
    }

    void "given 150 amount co-payment customer when requested amount below co-payment customer pays all"() {
        given:
        def coPay = CoPayment.amount(MonetaryAmount.from(150))
        when:
        def result = coPay.calculate(MonetaryAmount.from(100))
        then:
        result.amount == 100
    }
}
