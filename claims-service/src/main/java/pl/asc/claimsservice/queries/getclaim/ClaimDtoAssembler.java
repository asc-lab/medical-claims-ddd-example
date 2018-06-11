package pl.asc.claimsservice.queries.getclaim;

import lombok.AllArgsConstructor;
import pl.asc.claimsservice.domainmodel.Claim;
import pl.asc.claimsservice.domainmodel.ClaimItem;
import pl.asc.claimsservice.domainmodel.Person;
import pl.asc.claimsservice.queries.getclaim.dto.ClaimDto;
import pl.asc.claimsservice.queries.getclaim.dto.ClaimItemDto;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ClaimDtoAssembler {
    private Claim claim;

    static ClaimDtoAssembler from(Claim claim) {
        return new ClaimDtoAssembler(claim);
    }

    ClaimDto assembleDto() {
        if (claim==null) {
            return null;
        }

        return ClaimDto.builder()
                .id(claim.getId())
                .claimNumber(claim.getNumber())
                .policyNumber(claim.getPolicyVersion().getPolicy().getNumber())
                .policyHolder(assemblePolicyHolder(claim.getPolicyVersion().getPolicyHolder()))
                .eventDate(claim.getEventDate())
                .statusCode(claim.getStatus().toString())
                .paidByInsurerPart(claim.getEvaluation().getPaidByInsurer().getAmount())
                .paidByCustomerPart(claim.getEvaluation().getPaidByCustomer().getAmount())
                .items(assembleItems(claim))
                .build();
    }

    private List<ClaimItemDto> assembleItems(Claim claim) {
        return claim
                .getItems()
                .stream()
                .map(this::assembleItem)
                .collect(Collectors.toList());
    }

    private ClaimItemDto assembleItem(ClaimItem claimItem) {
        return ClaimItemDto.builder()
                .serviceCode(claimItem.getServiceCode())
                .quantity(claimItem.getQt().getValue())
                .price(claimItem.getPrice().getAmount())
                .paidByInsurerPart(claimItem.getEvaluation().getPaidByInsurer().getAmount())
                .paidByCustomerPart(claimItem.getEvaluation().getPaidByCustomer().getAmount())
                .build();
    }

    private String assemblePolicyHolder(Person policyHolder) {
        return policyHolder.getLastName() + " " + policyHolder.getFirstName() + " (" + policyHolder.getPesel() + ")";
    }
}
