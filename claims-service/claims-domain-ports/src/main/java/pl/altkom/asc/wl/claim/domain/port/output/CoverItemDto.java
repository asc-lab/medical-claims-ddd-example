package pl.altkom.asc.wl.claim.domain.port.output;

import lombok.Value;

@Value
public class CoverItemDto {

    private String code;
    private CoPaymentDto coPayment;
}
