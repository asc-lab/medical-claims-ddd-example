package pl.asc.claimsservice.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cover {
    @Id
    @GeneratedValue
    private Long id;

    private String code;

    @ManyToOne
    @JoinColumn(name = "POLICY_VERSION_ID")
    private PolicyVersion policyVersion;

    @OneToMany(mappedBy = "cover", cascade = CascadeType.ALL)
    private Set<Service> services;

    public Cover(String code) {
        this.code = code;
        this.services = new HashSet<>();
    }

    ServiceCollection services() {
        return new ServiceCollection(this, services);
    }
}
