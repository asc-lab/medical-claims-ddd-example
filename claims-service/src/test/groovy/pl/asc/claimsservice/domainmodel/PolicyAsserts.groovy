package pl.asc.claimsservice.domainmodel

class PolicyAsserts {
    private final Policy policy
    private final LimitConsumptionContainerCollection consumptions
    PolicyAsserts(Policy policy, LimitConsumptionContainerCollection consumptions) {
        this.policy = policy
        this.consumptions = consumptions
    }

    static of(Policy policy, LimitConsumptionContainerCollection consumptions) {
        return new PolicyAsserts(policy, consumptions)
    }

    boolean hasNoConsumptionForService(String serviceCode) {
        return consumptions.getConsumptionFor(PolicyRef.of(policy), ServiceCode.of(serviceCode)).size() == 0
    }

    boolean hasConsumptionForService(String serviceCode, BigDecimal expectedConsumptionAmount) {
        List<LimitConsumption> consumptions = consumptions.getConsumptionFor(PolicyRef.of(policy), ServiceCode.of(serviceCode))
        def sum = consumptions.sum { it -> it.amount.amount }
        return expectedConsumptionAmount.compareTo(sum) == 0
    }
}
