package pl.asc.claimsservice.domain

import pl.asc.claimsservice.shared.DateRange

import java.time.LocalDate

class PolicyBuilder {

    Policy build() {
        Policy policy = new Policy("P1212121")

        PolicyVersion versionOne =  policy.versions().add(
                1L,
                "Pakiet Gold",
                new Person("Jan", "Nowak", "111111116"),
                "2738123834783247723",
                DateRange.between(LocalDate.of(2018,1,1), LocalDate.of(2018,12,31)),
                DateRange.between(LocalDate.of(2018,1,1), LocalDate.of(9999,12,31)))

        Cover consultationOne = versionOne.covers().add("KONS")
        consultationOne.services().add("KONS_INTERNISTA", CoPayment.percent(0.25), Limit.amountForPolicyYear(100))
        consultationOne.services().add("KONS_PEDIATRA", CoPayment.amount(2.0), Limit.amountAndQuantityForPolicyYear(100, 20))

        Cover labOne = versionOne.covers().add("LAB")
        labOne.services().add("LAB_KREW_OB", CoPayment.percent(0.10), Limit.amountAndQuantityForPolicyYear(50, 5))
        labOne.services().add("LAB_HDL", CoPayment.amount(2.0), Limit.amountAndQuantityForPolicyYear(28, 2))

        PolicyVersion versionTwo =  policy.versions().add(
                2L,
                "Pakiet Gold",
                new Person("Jan", "Nowak", "111111116"),
                "2738123834783247723",
                DateRange.between(LocalDate.of(2018,1,1), LocalDate.of(2018,12,31)),
                DateRange.between(LocalDate.of(2018,7,1), LocalDate.of(9999,12,31)))

        Cover consultationTwo = versionTwo.covers().add("KONS")
        consultationTwo.services().add("KONS_INTERNISTA", CoPayment.percent(0.25), Limit.amountForPolicyYear(100))
        consultationTwo.services().add("KONS_PEDIATRA", CoPayment.amount(2.0), Limit.amountAndQuantityForPolicyYear(100, 20))

        Cover labTwo = versionTwo.covers().add("LAB")
        labTwo.services().add("LAB_KREW_OB", CoPayment.percent(0.10), Limit.amountAndQuantityForPolicyYear(50, 5))
        labTwo.services().add("LAB_HDL", CoPayment.amount(2.0), Limit.amountAndQuantityForPolicyYear(28, 2))




        return policy
    }
}
