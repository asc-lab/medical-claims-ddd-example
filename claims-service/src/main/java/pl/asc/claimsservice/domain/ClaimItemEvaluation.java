package pl.asc.claimsservice.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.asc.claimsservice.shared.primitives.MonetaryAmount;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ClaimItemEvaluation {
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

    static ClaimItemEvaluation paidByCustomer(ClaimItem claimItem) {
        return new ClaimItemEvaluation(
                claimItem.cost()
                ,MonetaryAmount.zero());
    }


    static ClaimItemEvaluation paidByInsurer(ClaimItem claimItem) {
        return new ClaimItemEvaluation(
                MonetaryAmount.zero(),
                claimItem.cost());
    }

    static ClaimEvaluation of(Set<ClaimItem> items) {
        MonetaryAmount paidByCustomer = items
                .stream()
                .filter(i -> i.getEvaluation()!=null)
                .map(i -> i.getEvaluation().paidByCustomer)
                .reduce(MonetaryAmount.zero(), MonetaryAmount::add);

        MonetaryAmount paidByInsurer = items
                .stream()
                .filter(i -> i.getEvaluation()!=null)
                .map(i -> i.getEvaluation().paidByInsurer)
                .reduce(MonetaryAmount.zero(), MonetaryAmount::add);

        return new ClaimEvaluation(paidByCustomer, paidByInsurer);
    }

    void applyCoPayment(MonetaryAmount coPaymentAmount) {
        this.paidByCustomer = this.paidByCustomer.add(coPaymentAmount);
        this.paidByInsurer = this.paidByInsurer.subtract(coPaymentAmount);
    }

    void applyLimit(MonetaryAmount paidByInsurerDueToLimit) {
        this.paidByCustomer = this.paidByCustomer.add(
                this.paidByInsurer.subtract(paidByInsurerDueToLimit)
        );
        this.paidByInsurer = paidByInsurerDueToLimit;
    }
}
