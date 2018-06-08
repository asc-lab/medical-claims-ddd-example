package pl.asc.claimsservice.domain;

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
        CoPayment coPayment = claim.getPolicyVersion().getCoPaymentFor(serviceCode);
        evaluation.applyCoPayment(coPayment.calculate(this));

        //apply limit
        Limit limit = claim.getPolicyVersion().getLimitFor(serviceCode);
        evaluation.applyLimit(limit.calculate(this));
    }

    void reject() {
        this.evaluation = ClaimItemEvaluation.paidByCustomer(this);
    }

    MonetaryAmount cost() {
        return qt.multiply(price);
    }


}
