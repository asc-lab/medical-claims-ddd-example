package pl.altkom.asc.wl.claim.domain;

import java.math.BigDecimal;

import lombok.Value;

@Value
class ClaimItem {
    private String code;
    private int qty;
    private BigDecimal price;

    BigDecimal wholeAmount() {
        return price.multiply(new BigDecimal(qty));
    }
}
