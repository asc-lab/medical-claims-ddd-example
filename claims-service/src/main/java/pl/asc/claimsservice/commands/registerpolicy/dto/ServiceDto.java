package pl.asc.claimsservice.commands.registerpolicy.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceDto {
    private String code;
    private CoPaymentDto coPayment;
    private LimitDto limit;
}
