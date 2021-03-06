package pl.asc.claimsservice.builders

import pl.asc.claimsservice.commands.registerpolicy.dto.*
import pl.asc.claimsservice.domainmodel.*
import pl.asc.claimsservice.shared.primitives.DateRange
import pl.asc.claimsservice.shared.primitives.MonetaryAmount
import pl.asc.claimsservice.shared.primitives.Percent
import pl.asc.claimsservice.shared.primitives.Quantity

import java.time.LocalDate

class PolicyBuilder {

    static Policy build() {
        Policy policy = new Policy("P1212121")

        PolicyVersion versionOne = policy.versions().add(
                1L,
                "Pakiet Gold",
                new Person("Jan", "Nowak", "111111116"),
                "2738123834783247723",
                DateRange.between(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31)),
                DateRange.between(LocalDate.of(2018, 1, 1), LocalDate.of(9999, 12, 31)))

        Cover consultationOne = versionOne.covers().add("KONS")
        consultationOne.services().add(
                "KONS_INTERNISTA",
                CoPayment.percent(Percent.of(25)),
                Limit.amountForPolicyYear(MonetaryAmount.from(100)))
        consultationOne.services().add(
                "KONS_PEDIATRA",
                CoPayment.amount(MonetaryAmount.from(2.0)),
                Limit.amountAndQuantityForPolicyYear(MonetaryAmount.from(100), Quantity.of(20)))

        Cover labOne = versionOne.covers().add("LAB")
        labOne.services().add(
                "LAB_KREW_OB",
                CoPayment.percent(Percent.of(10)),
                Limit.amountAndQuantityForPolicyYear(MonetaryAmount.from(50), Quantity.of(5)))
        labOne.services().add(
                "LAB_HDL",
                CoPayment.amount(MonetaryAmount.from(2.0)),
                Limit.amountAndQuantityForPolicyYear(MonetaryAmount.from(28), Quantity.of(2)))

        PolicyVersion versionTwo = policy.versions().add(
                2L,
                "Pakiet Gold",
                new Person("Jan", "Nowak", "111111116"),
                "2738123834783247723",
                DateRange.between(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31)),
                DateRange.between(LocalDate.of(2018, 7, 1), LocalDate.of(9999, 12, 31)))

        Cover consultationTwo = versionTwo.covers().add("KONS")
        consultationTwo.services().add(
                "KONS_INTERNISTA",
                CoPayment.percent(Percent.of(25)),
                Limit.amountForPolicyYear(MonetaryAmount.from(100)))
        consultationTwo.services().add(
                "KONS_PEDIATRA",
                CoPayment.amount(MonetaryAmount.from(2.0)),
                Limit.amountAndQuantityForPolicyYear(MonetaryAmount.from(100), Quantity.of(20)))

        Cover labTwo = versionTwo.covers().add("LAB")
        labTwo.services().add(
                "LAB_KREW_OB",
                CoPayment.percent(Percent.of(10)),
                Limit.amountAndQuantityForPolicyYear(MonetaryAmount.from(50), Quantity.of(5)))
        labTwo.services().add(
                "LAB_HDL",
                CoPayment.amount(MonetaryAmount.from(2.0)),
                Limit.amountAndQuantityForPolicyYear(MonetaryAmount.from(28), Quantity.of(2)))

        return policy
    }

    PolicyVersionDto buildDto(String number, Long version) {
        PolicyVersionDto pv = PolicyVersionDto.builder()
                .policyNumber(number)
                .productCode("ABO_GOLD")
                .policyHolder(new PersonDto("Jan", "Bak", "11111111116"))
                .accountNumber("901291092012910")
                .policyValidFrom(LocalDate.of(2018, 1, 1))
                .policyValidTo(LocalDate.of(2018, 12, 31))
                .versionNumber(version)
                .versionValidFrom(LocalDate.of(2018, 1, 1))
                .versionValidTo(LocalDate.of(2018, 12, 31))
                .covers([
                CoverDto.builder()
                        .code("C1")
                        .services([
                        ServiceDto.builder()
                                .code("S1")
                                .coPayment(new CoPaymentDto(10, null))
                                .limit(new LimitDto("POLICY_YEAR", 10, null))
                                .build()
                ]).build()
        ]).build()

        return pv
    }

    PolicyVersionDto buildDto() {
        return buildDto("P1", 1)
    }
}
