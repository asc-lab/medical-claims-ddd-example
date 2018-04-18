package pl.altkom.asc.wl.claim.app.infra.rest.submitclaim;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author tdorosz
 */
@Getter
@Setter
class ClaimPositionDto {
    private String serviceCode;
    private BigDecimal amount;
    private String currency;
    private Integer count;
}
