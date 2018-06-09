package pl.asc.claimsservice.commands.registerpolicy.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoPaymentDto {
    private BigDecimal percent;
    private BigDecimal amount;
}
