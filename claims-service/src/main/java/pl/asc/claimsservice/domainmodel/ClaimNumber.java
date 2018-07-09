package pl.asc.claimsservice.domainmodel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Value Object represents claim business id
 */
@Getter
@ToString
@EqualsAndHashCode
class ClaimNumber {
    private final String number;

    ClaimNumber(@NotEmpty String number) {
        this.number = number;
    }
}
