package pl.asc.claimsservice.domainmodel;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.asc.claimsservice.shared.primitives.DateRange;
import pl.asc.claimsservice.shared.primitives.MonetaryAmount;
import pl.asc.claimsservice.shared.primitives.Quantity;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class LimitConsumptionContainerCollection {
    private final List<LimitConsumptionContainer> consumptionContainers;

    LimitConsumptionContainer.Consumed getConsumptionFor(PolicyRef policy, LocalDate eventDate, ServiceCode serviceCode, DateRange period) {
        LimitConsumptionContainer container = getOrCreateContainer(policy, serviceCode);

        return container!=null ?
                container.consumed(period)
                : new LimitConsumptionContainer.Consumed(Quantity.zero(), MonetaryAmount.zero());
    }

    List<LimitConsumption> getConsumptionFor(PolicyRef policy, ServiceCode serviceCode) {
        LimitConsumptionContainer container = getOrCreateContainer(policy, serviceCode);

        return container!=null ?
                container.getConsumptions()
                : Lists.newArrayList();
    }

    void registerConsumption(ClaimItem item) {
        LimitConsumptionContainer container = getOrCreateContainer(item.getClaim().getPolicyVersionRef().policyRef(), item.getServiceCode());
        container.registerConsumption(item);
    }

    public void releaseConsumption(Claim claim) {
        for (ClaimItem item : claim.getItems()) {
            LimitConsumptionContainer container = getOrCreateContainer(claim.getPolicyVersionRef().policyRef(), item.getServiceCode());
            container.releaseConsumption(item);
        }
    }

    private LimitConsumptionContainer getOrCreateContainer(PolicyRef policy, ServiceCode serviceCode) {
        for (LimitConsumptionContainer c : consumptionContainers) {
            if (c.getServiceCode().equals(serviceCode)) {
                return c;
            }
        }

        return createContainer(policy, serviceCode);
        /*return consumptionContainers
                .stream()
                .filter(c->c.getServiceCode().equals(serviceCode))
                .findFirst()
                .orElse(createContainer(policy, serviceCode));*/
    }

    private LimitConsumptionContainer createContainer(PolicyRef policy, ServiceCode serviceCode) {
        LimitConsumptionContainer container = new LimitConsumptionContainer(policy, serviceCode);
        consumptionContainers.add(container);
        return container;
    }
}
