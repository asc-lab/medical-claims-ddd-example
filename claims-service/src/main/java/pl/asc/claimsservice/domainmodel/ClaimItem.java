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

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "code", column = @Column(name = "SERVICE_CODE"))
    )
    private ServiceCode serviceCode;

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

    void evaluate(ClaimEvaluationPolicy evaluationPolicy) {
        this.evaluation = evaluationPolicy.evaluateItem(this);
    }

    void reject() {
        this.evaluation = ClaimItemEvaluation.paidByCustomer(this);
    }

    MonetaryAmount cost() {
        return qt.multiply(price);
    }



}
