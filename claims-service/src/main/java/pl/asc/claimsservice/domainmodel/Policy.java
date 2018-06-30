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

    public Policy(String number) {
        this.number = number;
        this.versions = new HashSet<>();
    }

    public PolicyVersionCollection versions() {
        return new PolicyVersionCollection(this, versions);
    }
}
