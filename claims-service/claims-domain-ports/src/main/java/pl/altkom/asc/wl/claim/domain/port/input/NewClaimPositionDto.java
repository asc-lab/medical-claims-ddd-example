package pl.altkom.asc.wl.claim.domain.port.input;

import lombok.Value;

import java.math.BigDecimal;

/**
 * @author tdorosz
 */
@Value
public class NewClaimPositionDto {
    private String serviceCode;
    private BigDecimal amount;
    private String currency;
    private Integer count;
}
