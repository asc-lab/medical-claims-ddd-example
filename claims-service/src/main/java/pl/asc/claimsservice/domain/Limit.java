package pl.asc.claimsservice.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Limit {
    private LimitPeriod period;
    private BigDecimal maxAmount;
    private BigDecimal maxQuantity;

    static Limit amountForPolicyYear(BigDecimal maxAmount) {
        return new Limit(LimitPeriod.POLICY_YEAR, maxAmount, null);
    }

    static Limit quantityForPolicyYear(BigDecimal maxQuantity) {
        return new Limit(LimitPeriod.POLICY_YEAR, null, maxQuantity);
    }

    static Limit amountAndQuantityForPolicyYear(BigDecimal maxAmount, BigDecimal maxQuantity) {
        return new Limit(LimitPeriod.POLICY_YEAR, maxAmount, maxQuantity);
    }
}
