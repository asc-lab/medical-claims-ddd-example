package pl.altkom.asc.wl.claim.domain.port.input;

import lombok.Value;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author tdorosz
 */
@Value
public class NewClaimPositionDto {
    private String serviceCode;
    private BigDecimal amount;
    private Currency currency;
    private Integer count;
}
