package pl.asc.claimsservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    private ClaimStatus status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "claim")
    private Set<ClaimItem> items;

    public Claim(String number, LocalDate eventDate, Policy policy){
        this.number = number;
        this.policyVersion = policy.versions().validAtDate(eventDate);
        this.eventDate = eventDate;
        this.status = ClaimStatus.IN_EVALUATION;
        this.items = new HashSet<>();
    }


    void addItem(String serviceCode, BigDecimal qt, BigDecimal price) {
        ClaimItem item = new ClaimItem(null, this, serviceCode, qt, price, null);
        items.add(item);
    }

    public void evaluate() {
        if (!policyVersion.covers(this)){
            reject();
            return;
        }
        
    }

    private void reject() {
        //all money paid by customer
    }

    class ClaimEvaluationResult {
        //??
    }
}
