package pl.asc.claimsservice.queries.findclaim.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClaimViewDto {
    private String claimNumber;
    private String statusCode;
    private String policyNumber;
    private LocalDate eventDate;
    private BigDecimal amount;
    private BigDecimal amountPaidByInsurer;
    private BigDecimal amountPaidByCustomer;
}
