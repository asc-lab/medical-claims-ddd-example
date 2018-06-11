package pl.asc.claimsservice.queries.findclaim;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.asc.claimsservice.shared.cqs.Query;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class FindClaimQuery implements Query<FindClaimQueryResult> {
    private String claimNumber;
    private String statusCode;
    private String policyNumber;
    private LocalDate eventDateFrom;
    private LocalDate eventDateTo;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private String serviceCode;
    private String medicalServiceProviderCode;
}
