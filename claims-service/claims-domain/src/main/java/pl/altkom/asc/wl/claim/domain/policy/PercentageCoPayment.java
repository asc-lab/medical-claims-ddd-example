package pl.altkom.asc.wl.claim.domain.policy;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class PercentageCoPayment implements CoPayment {

    private static final BigDecimal HUNDRED = new BigDecimal("100");
    private int percent;

    public PercentageCoPayment(int percent) {
        Preconditions.checkState(Range.closed(0, 100).contains(percent));
        this.percent = percent;
    }

    @Override
    public BigDecimal customerContribution(BigDecimal serviceAmount) {
        return serviceAmount.multiply(new BigDecimal(percent)).divide(HUNDRED, 2, BigDecimal.ROUND_HALF_EVEN);
    }
}
