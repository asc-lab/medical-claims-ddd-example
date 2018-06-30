package pl.asc.claimsservice.domainmodel;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
class ClaimRef {
    private String number;

    static ClaimRef of(Claim claim) {
        return new ClaimRef(claim.getNumber());
    }
}
