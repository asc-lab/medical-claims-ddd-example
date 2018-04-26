package pl.altkom.asc.wl.claim.domain.policy;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class AmountCoPayment implements CoPayment {

    private BigDecimal amount;

    @Override
    public BigDecimal customerContribution(BigDecimal serviceAmount) {
        return serviceAmount.min(amount);
    }
}
