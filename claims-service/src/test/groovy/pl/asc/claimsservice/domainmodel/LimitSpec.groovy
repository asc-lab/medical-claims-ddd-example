package pl.asc.claimsservice.domainmodel

import pl.asc.claimsservice.shared.primitives.MonetaryAmount
import pl.asc.claimsservice.shared.primitives.Quantity
import spock.lang.Specification

class LimitSpec extends Specification {

    void "amount limit tests"(Quantity qt, MonetaryAmount price, MonetaryAmount coPayment, Quantity consumedQt,
                              MonetaryAmount consumedAmount, BigDecimal amount) {
        expect:
            def limit = Limit.amountForPolicyYear(MonetaryAmount.from(100))
            def paidByInsurer = limit.calculate(qt, price, coPayment, consumedQt, consumedAmount)
            paidByInsurer.amount == amount
        where:
            qt             | price                    | coPayment             | consumedQt      | consumedAmount           | amount
            Quantity.of(1) | MonetaryAmount.from(90)  | MonetaryAmount.zero() | Quantity.zero() | MonetaryAmount.zero()    | 90
            Quantity.of(1) | MonetaryAmount.from(120) | MonetaryAmount.zero() | Quantity.zero() | MonetaryAmount.zero()    | 100
            Quantity.of(1) | MonetaryAmount.from(90)  | MonetaryAmount.zero() | Quantity.zero() | MonetaryAmount.from(10)  | 90
            Quantity.of(1) | MonetaryAmount.from(120) | MonetaryAmount.zero() | Quantity.zero() | MonetaryAmount.from(20)  | 80
            Quantity.of(1) | MonetaryAmount.from(120) | MonetaryAmount.zero() | Quantity.zero() | MonetaryAmount.from(120) | 0
    }

    void "qt limit tests"(Quantity qt, MonetaryAmount price, MonetaryAmount coPayment, Quantity consumedQt,
                          MonetaryAmount consumedAmount, BigDecimal amount) {
        expect:
            def limit = Limit.quantityForPolicyYear(Quantity.of(10))
            def paidByInsurer = limit.calculate(qt, price, coPayment, consumedQt, consumedAmount)
            paidByInsurer.amount == amount
        where:
            qt              | price                    | coPayment             | consumedQt      | consumedAmount        | amount

            Quantity.of(9)  | MonetaryAmount.from(100) | MonetaryAmount.zero() | Quantity.zero() | MonetaryAmount.zero() | 900
            Quantity.of(11) | MonetaryAmount.from(100) | MonetaryAmount.zero() | Quantity.zero() | MonetaryAmount.zero() | 1000
            Quantity.of(11) | MonetaryAmount.from(100) | MonetaryAmount.zero() | Quantity.of(1)  | MonetaryAmount.zero() | 900
            Quantity.of(11) | MonetaryAmount.from(100) | MonetaryAmount.zero() | Quantity.of(2)  | MonetaryAmount.zero() | 800
            Quantity.of(11) | MonetaryAmount.from(100) | MonetaryAmount.zero() | Quantity.of(10) | MonetaryAmount.zero() | 0
    }

    //co payment test

    //amount and qt test
}
