package pl.altkom.asc.wl.claim.app.infra.storage;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimap;

import org.springframework.stereotype.Component;

import pl.altkom.asc.wl.claim.domain.port.output.CoPaymentDto;
import pl.altkom.asc.wl.claim.domain.port.output.CoverItemDto;
import pl.altkom.asc.wl.claim.domain.port.output.PolicyRepository;
import pl.altkom.asc.wl.claim.domain.port.output.PolicyVersionDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static com.google.common.collect.ImmutableSet.of;
import static pl.altkom.asc.wl.claim.domain.port.output.CoPaymentDto.CoPaymentType.AMOUNT;
import static pl.altkom.asc.wl.claim.domain.port.output.CoPaymentDto.CoPaymentType.PERCENT;

/**
 * @author tdorosz
 */
@Component
public class InMemoryPolicyRepositoryAdapter implements PolicyRepository {

    private final Multimap<String, PolicyVersionDto> repository;

    public InMemoryPolicyRepositoryAdapter() {
        final LocalDate now = LocalDate.now();
        this.repository = ImmutableSetMultimap.<String, PolicyVersionDto>builder()
                .put("1590N100", new PolicyVersionDto("1590N100", 1, now.minusMonths(11), now.plusMonths(1), ImmutableMap.of(
                        "KONS", of(
                                new CoverItemDto("KONS_INTERNISTA", new CoPaymentDto(PERCENT, new BigDecimal("25"))),
                                new CoverItemDto("KONS_PEDIATRA", new CoPaymentDto(AMOUNT, new BigDecimal("10")))
                        )
                ))).build();
    }

    @Override
    public Optional<PolicyVersionDto> lastVersion(String policyNumber) {
        return repository.get(policyNumber).stream()
                .max((pv1, pv2) -> Integer.compare(pv1.getVersion(), pv2.getVersion()));
    }

}
