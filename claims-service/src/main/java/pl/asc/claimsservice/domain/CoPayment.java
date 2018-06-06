package pl.asc.claimsservice.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class CoPayment {
    private BigDecimal amount;
    private BigDecimal percent;

    static CoPayment amount(BigDecimal amount) {
        return new CoPayment(amount,null);
    }

    static CoPayment percent(BigDecimal percent) {
        return new CoPayment(null,percent);
    }
}
