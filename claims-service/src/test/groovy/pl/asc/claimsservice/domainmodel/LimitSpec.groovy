package pl.asc.claimsservice.domainmodel

import pl.asc.claimsservice.shared.primitives.MonetaryAmount
import pl.asc.claimsservice.shared.primitives.Quantity
import spock.lang.Specification

class LimitSpec extends Specification {
    // amount limit tests

    void "when requested amount is below limit insurer pays all"() {
        given: "given 100 amount for policyRef year"
        def limit = Limit.amountForPolicyYear(MonetaryAmount.from(100))

        when: "previous consumption is 0 and we try to take 90"
        def paidByInsurer = limit.calculate(
                Quantity.of(1),
                MonetaryAmount.from(90),
                MonetaryAmount.zero(),
                Quantity.zero(),
                MonetaryAmount.zero())

        then: "all 90 is paid"
        paidByInsurer.amount == 90
    }

    void "when requested amount is above limit insurer pays up to limit"() {
        given: "given 100 amount for policyRef year"
        def limit = Limit.amountForPolicyYear(MonetaryAmount.from(100))

        when: "previous consumption is 0 and we try to take 120"
        def paidByInsurer = limit.calculate(
                Quantity.of(1),
                MonetaryAmount.from(120),
                MonetaryAmount.zero(),
                Quantity.zero(),
                MonetaryAmount.zero())

        then: "only 100 is paid"
        paidByInsurer.amount == 100
    }

    void "when requested amount plus perv consumption is below limit insurer pays all"() {
        given: "given 100 amount for policyRef year"
        def limit = Limit.amountForPolicyYear(MonetaryAmount.from(100))

        when: "previous consumption is 10 and we try to take 90"
        def paidByInsurer = limit.calculate(
                Quantity.of(1),
                MonetaryAmount.from(90),
                MonetaryAmount.zero(),
                Quantity.zero(),
                MonetaryAmount.from(10))

        then: "all 90 is paid"
        paidByInsurer.amount == 90
    }

    void "when requested amount plus prev consumption is above limit insurer pays up to limit"() {
        given: "given 100 amount for policyRef year"
        def limit = Limit.amountForPolicyYear(MonetaryAmount.from(100))

        when: "previous consumption is 20 and we try to take 120"
        def paidByInsurer = limit.calculate(
                Quantity.of(1),
                MonetaryAmount.from(120),
                MonetaryAmount.zero(),
                Quantity.zero(),
                MonetaryAmount.from(20))

        then: "only 80 is paid"
        paidByInsurer.amount == 80
    }


    void "when limit exhausted by prev consumptions insurer pays zero"() {
        given: "given 100 amount for policyRef year"
        def limit = Limit.amountForPolicyYear(MonetaryAmount.from(100))

        when: "previous consumption is 120 and we try to take 120"
        def paidByInsurer = limit.calculate(
                Quantity.of(1),
                MonetaryAmount.from(120),
                MonetaryAmount.zero(),
                Quantity.zero(),
                MonetaryAmount.from(120))

        then: "only 80 is paid"
        paidByInsurer.amount == 0
    }

    //qt limit tests
    void "when requested qt is below limit insurer pays all"() {
        given: "given 10 qt for policyRef year"
        def limit = Limit.quantityForPolicyYear(Quantity.of(10))

        when: "previous consumption is 0 and we try to take 9"
        def paidByInsurer = limit.calculate(
                Quantity.of(9),
                MonetaryAmount.from(100),
                MonetaryAmount.zero(),
                Quantity.zero(),
                MonetaryAmount.zero())

        then: "all 900 is paid"
        paidByInsurer.amount == 900
    }

    void "when requested qt is above limit insurer pays up to limit"() {
        given: "given 10 qt for policyRef year"
        def limit = Limit.quantityForPolicyYear(Quantity.of(10))

        when: "previous consumption is 0 and we try to take 11"
        def paidByInsurer = limit.calculate(
                Quantity.of(11),
                MonetaryAmount.from(100),
                MonetaryAmount.zero(),
                Quantity.zero(),
                MonetaryAmount.zero())

        then: "only 1000 is paid"
        paidByInsurer.amount == 1000
    }

    void "when requested qt plus prev consumption is below limit insurer pays all"() {
        given: "given 10 qt for policyRef year"
        def limit = Limit.quantityForPolicyYear(Quantity.of(10))

        when: "previous consumption is 1 and we try to take 9"
        def paidByInsurer = limit.calculate(
                Quantity.of(9),
                MonetaryAmount.from(100),
                MonetaryAmount.zero(),
                Quantity.of(1),
                MonetaryAmount.zero())

        then: "all 900 is paid"
        paidByInsurer.amount == 900
    }

    void "when requested qt plus prev consumption is above limit insurer pays up to limit"() {
        given: "given 10 qt for policyRef year"
        def limit = Limit.quantityForPolicyYear(Quantity.of(10))

        when: "previous consumption is 2 and we try to take 9"
        def paidByInsurer = limit.calculate(
                Quantity.of(9),
                MonetaryAmount.from(100),
                MonetaryAmount.zero(),
                Quantity.of(2),
                MonetaryAmount.zero())

        then: "only 800 is paid"
        paidByInsurer.amount == 800
    }

    void "when limit is exhausted by prev qt consumption insurer pays zero"() {
        given: "given 10 qt for policyRef year"
        def limit = Limit.quantityForPolicyYear(Quantity.of(10))

        when: "previous consumption is 10 and we try to take 9"
        def paidByInsurer = limit.calculate(
                Quantity.of(9),
                MonetaryAmount.from(100),
                MonetaryAmount.zero(),
                Quantity.of(10),
                MonetaryAmount.zero())

        then: "zero is paid"
        paidByInsurer.amount == 0
    }

    //co payment test


    //amount and qt test
}
