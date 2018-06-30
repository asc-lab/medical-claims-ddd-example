package pl.asc.claimsservice.queries.getclaim;

import lombok.AllArgsConstructor;
import pl.asc.claimsservice.domainmodel.*;
import pl.asc.claimsservice.queries.getclaim.dto.ClaimDto;
import pl.asc.claimsservice.queries.getclaim.dto.ClaimItemDto;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ClaimDtoAssembler {
    private Claim claim;
    private PolicyVersion policyVersion;

    static ClaimDtoAssembler from(Claim claim, Policy policy) {
        return new ClaimDtoAssembler(claim, policy.versions().withNumber(claim.getPolicyVersionRef().getVersionNumber()));
    }

    ClaimDto assembleDto() {
        if (claim==null) {
            return null;
        }

        return ClaimDto.builder()
                .id(claim.getId())
                .claimNumber(claim.getNumber())
                .policyNumber(claim.getPolicyVersionRef().getPolicyNumber())
                .policyHolder(assemblePolicyHolder(policyVersion.getPolicyHolder()))
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
                .serviceCode(claimItem.getServiceCode().getCode())
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
