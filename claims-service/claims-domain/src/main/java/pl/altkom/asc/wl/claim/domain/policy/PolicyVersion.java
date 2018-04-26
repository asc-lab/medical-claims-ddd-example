package pl.altkom.asc.wl.claim.domain.policy;

import com.google.common.collect.Range;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import lombok.Value;

@Value
public class PolicyVersion {

    private String number;
    private int version;
    private LocalDate validFrom;
    private LocalDate validTo;
    private Set<Cover> covers;

    public boolean underProtection(LocalDateTime moment) {
        return Range.closed(validFrom, validTo).contains(moment.toLocalDate());
    }

    public Optional<Cover> findCoverByService(String serviceCode) {
        return covers.stream()
                .filter(cover -> cover.findService(serviceCode).isPresent())
                .findFirst();
    }
}
