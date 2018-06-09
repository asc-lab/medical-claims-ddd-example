package pl.asc.claimsservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.asc.claimsservice.shared.primitives.DateRange;
import pl.asc.claimsservice.shared.primitives.MonetaryAmount;
import pl.asc.claimsservice.shared.primitives.Quantity;

import javax.persistence.*;
import java.time.LocalDate;
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
    @ManyToOne
    @JoinColumn(name = "POLICY_ID")
    private Policy policy;
    private String serviceCode;
    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL)
    private List<LimitConsumption> consumptions;

    LimitConsumptionContainer(Policy policy, String serviceCode) {
        this.policy = policy;
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

    void registerConsumption(LocalDate eventDate, Quantity qt, MonetaryAmount cost) {
        LimitConsumption newConsumption = new LimitConsumption(this, eventDate, qt, cost);
        consumptions.add(newConsumption);
    }

    @AllArgsConstructor
    @Getter
    static class Consumed {
        private Quantity quantity;
        private MonetaryAmount amount;
    }
}
