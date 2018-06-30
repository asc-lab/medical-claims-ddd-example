package pl.asc.claimsservice.domainmodel;

import pl.asc.claimsservice.commands.submitclaim.SubmitClaimCommand;
import pl.asc.claimsservice.shared.primitives.MonetaryAmount;
import pl.asc.claimsservice.shared.primitives.Quantity;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public class ClaimFactory {
    private Policy policy;
    private String claimNumber;
    private LocalDate eventDate;
    private Set<SubmitClaimCommand.Item> items;

    public ClaimFactory(Policy policy) {
        this.policy = policy;
    }

    public static ClaimFactory forPolicy(Optional<Policy> policy) {
        return new ClaimFactory(policy.get());
    }

    public ClaimFactory withNumber(String number) {
        this.claimNumber = number;
        return this;
    }

    public ClaimFactory withEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    public ClaimFactory withItems(Set<SubmitClaimCommand.Item> items) {
        this.items = items;
        return this;
    }

    public Claim create() {
        Claim claim = new Claim(claimNumber, eventDate, policy);
        this.items.forEach(i -> claim.addItem(ServiceCode.of(i.getServiceCode()), Quantity.of(i.getQuantity()), MonetaryAmount.from(i.getPrice())));
        return claim;
    }
}
