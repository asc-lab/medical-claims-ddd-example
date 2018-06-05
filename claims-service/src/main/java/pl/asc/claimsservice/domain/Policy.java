package pl.asc.claimsservice.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.asc.claimsservice.shared.DateRange;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Policy {
    @Id
    @GeneratedValue
    private Long id;
    private String  number;
    @Embedded
    private DateRange coverPeriod;

    public boolean covers(Claim claim){
        return coverPeriod.contains(claim.getEventDate()) && this.equals(claim.getPolicy());
    }

}
