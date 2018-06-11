package pl.asc.claimsservice.queries.getclaim.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClaimDto {
    private Long id;
    private String claimNumber;
    private String policyNumber;
    private String policyHolder;
    private LocalDate eventDate;
    private String statusCode;
    private BigDecimal paidByInsurerPart;
    private BigDecimal paidByCustomerPart;
    private List<ClaimItemDto> items;
}
