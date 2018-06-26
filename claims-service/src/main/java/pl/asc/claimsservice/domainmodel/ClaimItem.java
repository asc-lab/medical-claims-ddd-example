package pl.asc.claimsservice.domainmodel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.asc.claimsservice.shared.primitives.MonetaryAmount;
import pl.asc.claimsservice.shared.primitives.Quantity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ClaimItem {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLAIM_ID")
    private Claim claim;

    private String serviceCode;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "QT"))
    })
    private Quantity qt;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "PRICE"))
    })
    private MonetaryAmount price;

    @Embedded
    private ClaimItemEvaluation evaluation;

    void evaluate() {
        //assume insurer pays all
        evaluation = ClaimItemEvaluation.paidByInsurer(this);

        //check if covered by policy
        if (ClaimItemNotCovered.isSatisfied(this)) {
            reject();
            return;
        }

        //apply coPayment
        MonetaryAmount coPayment = claim.getPolicyVersion().getCoPaymentFor(serviceCode).calculate(this.cost());
        evaluation.applyCoPayment(coPayment);

        //apply limit
        Limit limit = claim.getPolicyVersion().getLimitFor(serviceCode);
        LimitConsumptionContainer.Consumed consumed = claim
                .getPolicyVersion()
                .getPolicy()
                .consumptionContainers()
                .getConsumptionFor(claim.getEventDate(), serviceCode, limit.getPeriod());
        evaluation.applyLimit(limit.calculate(qt, price, coPayment, consumed.getQuantity(), consumed.getAmount()));

        claim.getPolicyVersion().getPolicy().consumptionContainers().registerConsumption(this);
    }

    void reject() {
        this.evaluation = ClaimItemEvaluation.paidByCustomer(this);
        claim.getPolicyVersion().getPolicy().consumptionContainers().releaseConsumption(this);
    }

    MonetaryAmount cost() {
        return qt.multiply(price);
    }


}
