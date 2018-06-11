package pl.asc.claimsservice.domainmodel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.asc.claimsservice.shared.primitives.MonetaryAmount;

import javax.persistence.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ClaimEvaluation {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "PAID_BY_CUSTOMER"))
    })
    private MonetaryAmount paidByCustomer;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "PAID_BY_INSURER"))
    })
    private MonetaryAmount paidByInsurer;
}
