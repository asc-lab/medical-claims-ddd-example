package pl.altkom.asc.wl.claim.domain;

import com.google.common.collect.Range;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Value;

@Value
class PolicyVersion {

    private int version;
    private LocalDate validFrom;
    private LocalDate validTo;

    public boolean underProtection(LocalDateTime moment) {
        return Range.closed(validFrom, validTo).contains(moment.toLocalDate());
    }
}
