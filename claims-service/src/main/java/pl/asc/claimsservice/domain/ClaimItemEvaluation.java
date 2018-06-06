package pl.asc.claimsservice.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ClaimItemEvaluation {
    private BigDecimal paidByCustomer;
    private BigDecimal paidByInsurer;
}
