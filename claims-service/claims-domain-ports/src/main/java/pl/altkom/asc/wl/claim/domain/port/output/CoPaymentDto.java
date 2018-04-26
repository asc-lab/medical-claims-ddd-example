package pl.altkom.asc.wl.claim.domain.port.output;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class CoPaymentDto {

    private CoPaymentType type;
    private BigDecimal value;

    public enum CoPaymentType {
        AMOUNT, PERCENT;
    }
}
