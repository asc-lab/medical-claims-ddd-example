package pl.asc.claimsservice.domainmodel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Policy {
    @Id
    @GeneratedValue
    private Long id;

    private String  number;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL)
    private Set<PolicyVersion> versions;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL)
    private List<LimitConsumptionContainer> consumptionContainers;

    public Policy(String number) {
        this.number = number;
        this.versions = new HashSet<>();
        this.consumptionContainers = new ArrayList<>();
    }

    PolicyVersionCollection versions() {
        return new PolicyVersionCollection(this, versions);
    }

    LimitConsumptionContainerCollection consumptionContainers() {
        return new LimitConsumptionContainerCollection(consumptionContainers,this);
    }
}
