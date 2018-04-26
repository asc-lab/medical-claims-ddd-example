package pl.altkom.asc.wl.claim.domain.policy;

import lombok.Value;

@Value
public class CoverItem {
    private String code;
    private CoPayment coPayment;
    private CoverLimit limit;
}
