package pl.asc.claimsservice.readmodel;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.asc.claimsservice.domainmodel.Claim;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class ClaimViewAssembler {

    static ClaimView map(Claim claim) {
        return new ClaimView(
                null,
                claim.getNumber(),
                claim.getStatus().toString(),
                claim.getPolicyVersionRef().getPolicyNumber(),
                claim.getEventDate(),
                claim.getEvaluation().getPaidByCustomer().add(claim.getEvaluation().getPaidByInsurer()).getAmount(),
                claim.getEvaluation().getPaidByInsurer().getAmount(),
                claim.getEvaluation().getPaidByCustomer().getAmount(),
                claim.getItems().stream().map(s -> s.getServiceCode().getCode()).collect(Collectors.toList())
        );
    }
}
