package pl.altkom.asc.wl.claim.domain.policy;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CoverLimit {
    private int maxQty;
    private int maxAmount;
    private LimitPeriod period;
}
