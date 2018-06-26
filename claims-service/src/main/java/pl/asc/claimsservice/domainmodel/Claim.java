package pl.asc.claimsservice.domainmodel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.asc.claimsservice.shared.primitives.MonetaryAmount;
import pl.asc.claimsservice.shared.primitives.Quantity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

@Entity
@NoArgsConstructor
@Getter
public class Claim {
    @Id
    @GeneratedValue
    Long id;

    private String number; //make VO

    @ManyToOne()
    @JoinColumn(name = "POLICY_VERSION_ID")
    private PolicyVersion policyVersion;

    private LocalDate eventDate;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status;

    private ClaimEvaluation evaluation;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "claim")
    private Set<ClaimItem> items;

    public Claim(String number, LocalDate eventDate, Policy policy){
        this.number = number;
        this.policyVersion = policy.versions().validAtDate(eventDate);
        this.eventDate = eventDate;
        this.status = ClaimStatus.IN_EVALUATION;
        this.evaluation = null;
        this.items = new HashSet<>();
    }

    public void evaluate() {
        checkState(status.allowsEdition());

        if (!policyVersion.covers(this)){
            rejectAllItems();
            status = ClaimStatus.REJECTED;
            return;
        }

        items.forEach(i -> i.evaluate());

        evaluation = ClaimItemEvaluation.of(this.items);
        status = ClaimStatus.EVALUATED;
    }

    public void reject() {
        checkState(status.allowsRejection());
        rejectAllItems();
        status = ClaimStatus.REJECTED;
    }

    public void accept() {
        checkState(status.allowsAcceptance());
        status = ClaimStatus.ACCEPTED;
    }

    void addItem(String serviceCode, Quantity qt, MonetaryAmount price) {
        checkState(status.allowsEdition());
        ClaimItem item = new ClaimItem(null, this, serviceCode, qt, price, null);
        items.add(item);
    }

    private void rejectAllItems() {
        items.forEach(i -> i.reject());
        evaluation = ClaimItemEvaluation.of(this.items);
    }
}
