package pl.asc.claimsservice.domainmodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.asc.claimsservice.shared.primitives.DateRange;
import pl.asc.claimsservice.shared.primitives.MonetaryAmount;
import pl.asc.claimsservice.shared.primitives.Quantity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LimitConsumptionContainer
{
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "policyNumber", column = @Column(name = "POLICY_NUMBER"))
    )
    private PolicyRef policyRef;

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "code", column = @Column(name = "SERVICE_CODE"))
    )
    private ServiceCode serviceCode;

    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL)
    private List<LimitConsumption> consumptions;

    LimitConsumptionContainer(PolicyRef policy, ServiceCode serviceCode) {
        this.policyRef = policy;
        this.serviceCode = serviceCode;
        this.consumptions = new ArrayList<>();
    }

    Consumed consumed(DateRange period) {
        List<LimitConsumption> consumptionsInPeriod = consumptions
                .stream()
                .filter(c -> period.contains(c.getDate()))
                .collect(Collectors.toList());

        Quantity consumedQt = consumptionsInPeriod
                .stream()
                .map(c -> c.getQuantity())
                .reduce(Quantity.zero(), Quantity::add);

        MonetaryAmount consumedAmount = consumptionsInPeriod
                .stream()
                .map(c -> c.getAmount())
                .reduce(MonetaryAmount.zero(), MonetaryAmount::add);

        return new Consumed(consumedQt, consumedAmount);
    }

    void releaseConsumption(ClaimItem item) {
        this.consumptions.removeIf(cons -> cons.getConsumptionSource().equals(ClaimRef.of(item.getClaim())));
    }

    public void registerConsumption(ClaimItem item) {
        LimitConsumption newConsumption = new LimitConsumption(
                this,
                item);
        consumptions.add(newConsumption);
    }

    @AllArgsConstructor
    @Getter
    static class Consumed {
        private Quantity quantity;
        private MonetaryAmount amount;
    }
}
