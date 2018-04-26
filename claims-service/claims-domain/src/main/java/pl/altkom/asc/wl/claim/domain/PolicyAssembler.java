package pl.altkom.asc.wl.claim.domain;

import pl.altkom.asc.wl.claim.domain.policy.AmountCoPayment;
import pl.altkom.asc.wl.claim.domain.policy.Cover;
import pl.altkom.asc.wl.claim.domain.policy.CoverItem;
import pl.altkom.asc.wl.claim.domain.policy.PercentageCoPayment;
import pl.altkom.asc.wl.claim.domain.policy.PolicyVersion;
import pl.altkom.asc.wl.claim.domain.port.output.CoPaymentDto;
import pl.altkom.asc.wl.claim.domain.port.output.CoverItemDto;
import pl.altkom.asc.wl.claim.domain.port.output.PolicyVersionDto;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author tdorosz
 */
class PolicyAssembler {

    PolicyVersion from(PolicyVersionDto dto) {
        return new PolicyVersion(dto.getPolicyNumber(), dto.getVersion(), dto.getValidFrom(), dto.getValidTo(), covers(dto));
    }

    private Set<Cover> covers(PolicyVersionDto dto) {
        return dto.getCovers().entrySet().stream()
                .map(entry -> new Cover(entry.getKey(), coveredItems(entry.getValue())))
                .collect(Collectors.toSet());
    }

    private Set<CoverItem> coveredItems(Set<CoverItemDto> dtos) {
        return dtos.stream().map(this::coverItem).collect(Collectors.toSet());
    }

    private CoverItem coverItem(CoverItemDto item) {
        final CoPaymentDto coPaymentDto = item.getCoPayment();
        final CoPaymentDto.CoPaymentType coPaymentType = coPaymentDto.getType();
        switch (coPaymentType) {
            case AMOUNT:
                return new CoverItem(item.getCode(), new AmountCoPayment(coPaymentDto.getValue()), null);
            case PERCENT:
                return new CoverItem(item.getCode(), new PercentageCoPayment(coPaymentDto.getValue().intValue()), null);
            default:
                throw new IllegalStateException(String.format("co payment type: %s not supported", coPaymentType));
        }
    }
}
