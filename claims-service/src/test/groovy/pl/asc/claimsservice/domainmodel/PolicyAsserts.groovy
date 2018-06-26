package pl.asc.claimsservice.domainmodel

class PolicyAsserts {
    private final Policy policy

    PolicyAsserts(Policy policy) {
        this.policy = policy
    }

    static of(Policy policy) {
        return new PolicyAsserts(policy)
    }

    boolean hasNoConsumptionForService(String serviceCode) {
        return policy.consumptionContainers().getConsumptionFor(serviceCode).size() == 0
    }

    boolean hasConsumptionForService(String serviceCode, BigDecimal expectedConsumptionAmount) {
        List<LimitConsumption> consumptions = policy.consumptionContainers().getConsumptionFor(serviceCode)
        def sum = consumptions.sum { it -> it.amount.amount }
        return expectedConsumptionAmount.compareTo(sum) == 0
    }
}
