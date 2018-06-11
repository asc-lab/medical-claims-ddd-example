package pl.asc.claimsservice.domainmodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.asc.claimsservice.shared.primitives.MonetaryAmount;
import pl.asc.claimsservice.shared.primitives.Quantity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LimitConsumption {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "CONTAINER_ID")
    private LimitConsumptionContainer container;
    private LocalDate date;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "CONSUMED_QT"))
    })
    private Quantity quantity;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "CONSUMED_AMOUNT"))
    })
    private MonetaryAmount amount;

    public LimitConsumption(LimitConsumptionContainer container, LocalDate date, Quantity quantity, MonetaryAmount amount) {
        this.container = container;
        this.date = date;
        this.quantity = quantity;
        this.amount = amount;
    }
}
