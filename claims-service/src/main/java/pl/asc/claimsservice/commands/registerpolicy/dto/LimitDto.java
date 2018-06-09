package pl.asc.claimsservice.commands.registerpolicy.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LimitDto {
    private String periodTypeCode;
    private BigDecimal maxQuantity;
    private BigDecimal maxAmount;
}
