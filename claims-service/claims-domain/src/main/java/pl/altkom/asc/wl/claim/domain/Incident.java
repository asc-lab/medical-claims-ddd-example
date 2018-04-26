package pl.altkom.asc.wl.claim.domain;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Value;

@Value
public class Incident {
    private LocalDateTime incidentTime;
    private String medCode;
    private Set<ClaimItem> items;
}
