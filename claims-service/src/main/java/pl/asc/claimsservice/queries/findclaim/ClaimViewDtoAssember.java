package pl.asc.claimsservice.queries.findclaim;


import lombok.AllArgsConstructor;
import pl.asc.claimsservice.queries.findclaim.dto.ClaimViewDto;
import pl.asc.claimsservice.readmodel.ClaimView;

@AllArgsConstructor
public class ClaimViewDtoAssember {
    private ClaimView claim;

    static ClaimViewDtoAssember from(ClaimView claim) {
        return new ClaimViewDtoAssember(claim);
    }

    ClaimViewDto assembleDto() {
        return new ClaimViewDto(
                claim.getClaimNumber(),
                claim.getStatusCode(),
                claim.getPolicyNumber(),
                claim.getEventDate(),
                claim.getAmount(),
                claim.getAmountPaidByInsurer(),
                claim.getAmountPaidByCustomer()
        );
    }
}
