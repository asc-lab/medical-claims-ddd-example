package pl.asc.claimsservice.queries.findclaim;


import lombok.AllArgsConstructor;
import pl.asc.claimsservice.queries.findclaim.dto.ClaimViewDto;
import pl.asc.claimsservice.readmodel.ClaimView;

@AllArgsConstructor
public class ClaimViewDtoAssembler {
    private ClaimView claim;

    static ClaimViewDtoAssembler from(ClaimView claim) {
        return new ClaimViewDtoAssembler(claim);
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
