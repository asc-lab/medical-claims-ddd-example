package pl.asc.claimsservice.domainmodel

import pl.asc.claimsservice.shared.primitives.MonetaryAmount
import pl.asc.claimsservice.shared.primitives.Percent
import spock.lang.Specification

class CoPaymentSpec extends Specification {
    void "customers pay for given co-payment"(CoPayment coPay, BigDecimal amount) {
        expect:
            def result = coPay.calculate(MonetaryAmount.from(100))
            result.amount == amount
        where:
            coPay                                      | amount
            CoPayment.empty()                          | 0
            CoPayment.percent(Percent.of(10))          | 10
            CoPayment.amount(MonetaryAmount.from(15))  | 15
            CoPayment.amount(MonetaryAmount.from(150)) | 100
    }
}
