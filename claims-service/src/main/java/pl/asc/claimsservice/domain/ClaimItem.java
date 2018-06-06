package pl.asc.claimsservice.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ClaimItem {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLAIM_ID")
    private Claim claim;

    private String serviceCode;

    private BigDecimal qt;

    private BigDecimal price;

    @Embedded
    private ClaimItemEvaluation evaluation;

}
