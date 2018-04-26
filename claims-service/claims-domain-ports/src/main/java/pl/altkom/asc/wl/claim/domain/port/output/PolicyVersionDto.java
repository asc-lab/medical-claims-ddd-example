package pl.altkom.asc.wl.claim.domain.port.output;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import lombok.Value;

/**
 * @author tdorosz
 */
@Value
public class PolicyVersionDto {
    private String policyNumber;
    private int version;
    private LocalDate validFrom;
    private LocalDate validTo;
    private Map<String, Set<CoverItemDto>> covers;
}
