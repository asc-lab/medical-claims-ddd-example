package pl.asc.claimsservice.domain;

import lombok.*;
import pl.asc.claimsservice.shared.primitives.MonetaryAmount;
import pl.asc.claimsservice.shared.primitives.Quantity;

import javax.persistence.*;


@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Limit {
    @Enumerated(EnumType.STRING)
    private LimitPeriod period;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "LIMIT_AMOUNT"))
    })
    private MonetaryAmount maxAmount;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "LIMIT_QT"))
    })
    private Quantity maxQuantity;

    static Limit amountForPolicyYear(MonetaryAmount maxAmount) {
        return new Limit(LimitPeriod.POLICY_YEAR, maxAmount, null);
    }

    static Limit quantityForPolicyYear(Quantity maxQuantity) {
        return new Limit(LimitPeriod.POLICY_YEAR, null, maxQuantity);
    }

    static Limit amountAndQuantityForPolicyYear(MonetaryAmount maxAmount, Quantity maxQuantity) {
        return new Limit(LimitPeriod.POLICY_YEAR, maxAmount, maxQuantity);
    }

    static Limit empty() {
        return new Limit(null,null,null);
    }

    MonetaryAmount calculate(ClaimItem claimItem) {
        MonetaryAmount priceAfterCoPayment = claimItem.getPrice()
                .subtract(claimItem.getEvaluation().getPaidByCustomer().divide(claimItem.getQt()));

        Quantity qtToPayByInsurer = claimItem.getQt();

        if (maxQuantity!=null) {
            qtToPayByInsurer = Quantity.min(maxQuantity, qtToPayByInsurer);
        }

        MonetaryAmount amtToPayByInsurer = qtToPayByInsurer.multiply(priceAfterCoPayment);

        if (maxAmount!=null) {
            amtToPayByInsurer = MonetaryAmount.min(maxAmount, amtToPayByInsurer);
        }

        return amtToPayByInsurer;
    }
}
