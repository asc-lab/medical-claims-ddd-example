package pl.asc.claimsservice.readmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ClaimView {
    @Id
    @GeneratedValue
    Long id;

    private String claimNumber;

    private String statusCode;

    private String policyNumber;

    private LocalDate eventDate;

    private BigDecimal amount;

    private BigDecimal amountPaidByInsurer;

    private BigDecimal amountPaidByCustomer;

    @ElementCollection
    @CollectionTable(name = "CLAIM_VIEW_SERVICE_CODE", joinColumns = @JoinColumn(name = "CLAIM_VIEW_ID"))
    @Column(name = "SERVICE_CODE")
    private List<String> serviceCode;
}
