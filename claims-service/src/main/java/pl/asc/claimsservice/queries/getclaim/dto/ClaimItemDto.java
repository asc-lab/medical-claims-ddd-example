package pl.asc.claimsservice.queries.getclaim.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClaimItemDto {
    private String serviceCode;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal paidByInsurerPart;
    private BigDecimal paidByCustomerPart;

}
