package pl.altkom.asc.wl.claim.domain.policy;

import java.math.BigDecimal;

public interface CoPayment {

    BigDecimal customerContribution(BigDecimal serviceAmount);
}
