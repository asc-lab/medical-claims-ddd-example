package pl.asc.claimsservice.domainmodel;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import pl.asc.claimsservice.shared.primitives.MonetaryAmount;
import pl.asc.claimsservice.shared.primitives.Quantity;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class LimitConsumptionContainerCollection {
    private final List<LimitConsumptionContainer> consumptionContainers;
    private final Policy policy;

    LimitConsumptionContainer.Consumed getConsumptionFor(LocalDate eventDate, String serviceCode, LimitPeriod period) {
        LimitConsumptionContainer container = getOrCreateContainer(serviceCode);

        return container!=null ?
                container.consumed(period.calculateDateRange(eventDate,policy.versions().validAtDate(eventDate)))
                : new LimitConsumptionContainer.Consumed(Quantity.zero(), MonetaryAmount.zero());
    }

    List<LimitConsumption> getConsumptionFor(String serviceCode) {
        LimitConsumptionContainer container = getOrCreateContainer(serviceCode);

        return container!=null ?
                container.getConsumptions()
                : Lists.newArrayList();
    }

    void registerConsumption(ClaimItem item) {
        LimitConsumptionContainer container = getOrCreateContainer(item.getServiceCode());
        container.registerConsumption(item);
    }

    void releaseConsumption(ClaimItem item) {
        LimitConsumptionContainer container = getOrCreateContainer(item.getServiceCode());
        container.releaseConsumption(item);
    }

    private LimitConsumptionContainer getOrCreateContainer(String serviceCode) {
        return consumptionContainers
                .stream()
                .filter(c->c.getServiceCode().equals(serviceCode))
                .findFirst()
                .orElse(createContainer(serviceCode));
    }

    private LimitConsumptionContainer createContainer(String serviceCode) {
        LimitConsumptionContainer container = new LimitConsumptionContainer(policy,serviceCode);
        consumptionContainers.add(container);
        return container;
    }
}
