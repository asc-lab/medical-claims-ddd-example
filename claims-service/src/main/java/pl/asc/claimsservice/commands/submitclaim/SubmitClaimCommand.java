package pl.asc.claimsservice.commands.submitclaim;

import de.triology.cb.Command;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitClaimCommand implements Command<SubmitClaimResult> {

    @NotNull
    private String policyNumber;
    @NotNull
    private LocalDate eventDate;
    @NotNull
    private String medicalServiceProviderCode;
    @NotNull @NotEmpty
    private Set<Item> items;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private String serviceCode;
        private BigDecimal quantity;
        private BigDecimal price;
    }
}
