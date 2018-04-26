package pl.altkom.asc.wl.claim.domain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import pl.altkom.asc.wl.claim.domain.event.DomainEvent;
import pl.altkom.asc.wl.claim.domain.event.SubmittedClaimPreliminaryAccepted;
import pl.altkom.asc.wl.claim.domain.event.SubmittedClaimRejected;
import pl.altkom.asc.wl.claim.domain.policy.CoPayment;
import pl.altkom.asc.wl.claim.domain.policy.Cover;
import pl.altkom.asc.wl.claim.domain.policy.CoverItem;
import pl.altkom.asc.wl.claim.domain.policy.PolicyVersion;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.Getter;

import static java.lang.String.format;
import static pl.altkom.asc.wl.claim.domain.Commitment.customerOnly;
import static pl.altkom.asc.wl.claim.domain.Commitment.zero;

class SubmittedClaim {

    @Getter
    private final UUID uuid;
    private String policyNumber;
    private Incident incident;
    private SubmittedClaimState state;
    private Commitment commitment;

    private List<DomainEvent> dirtyEvents = Lists.newArrayList();

    public SubmittedClaim(UUID uuid) {
        this.uuid = uuid;
    }

    void submitted(PolicyVersion policyVersion, Incident incident) {
        if (!policyVersion.underProtection(incident.getIncidentTime())) {
            lackOfCoverageRejected(new SubmittedClaimRejected(uuid, Instant.now(), policyVersion, incident));
        } else {
            preliminaryAccepted(new SubmittedClaimPreliminaryAccepted(uuid, Instant.now(), policyVersion, incident));
        }
    }

    private void lackOfCoverageRejected(SubmittedClaimRejected event) {
        this.policyNumber = event.getPolicyVersion().getNumber();
        this.incident = event.getIncident();
        this.state = SubmittedClaimState.REJECTED;
        this.commitment = Commitment.zero();
        dirtyEvents.add(event);
    }

    private void preliminaryAccepted(SubmittedClaimPreliminaryAccepted event) {
        this.policyNumber = event.getPolicyVersion().getNumber();
        this.incident = event.getIncident();
        this.state = SubmittedClaimState.PRELIMINARY_ACCEPTED;
        this.commitment = incident.getItems().stream()
                .map(claimItem -> singleCalculation(event.getPolicyVersion(), claimItem))
                .reduce(zero(), Commitment::add);
        dirtyEvents.add(event);
    }

    private Commitment singleCalculation(PolicyVersion version, ClaimItem claimItem) {
        final String serviceCode = claimItem.getCode();
        final BigDecimal amount = claimItem.wholeAmount();
        Optional<Cover> optCover = version.findCoverByService(serviceCode);
        if (!optCover.isPresent()) {
            return customerOnly(amount);
        }
        return calculateCoveredCommitment(serviceCode, amount, optCover.get());
    }

    private Commitment calculateCoveredCommitment(String serviceCode, BigDecimal amount, Cover cover) {
        CoverItem service = cover.findService(serviceCode)
                .orElseThrow(() -> new IllegalStateException(format("Service: %s should be already available from cover: %s", serviceCode, cover.getCode())));
        final CoPayment serviceCoPayment = service.getCoPayment();
        final BigDecimal customerContribution = serviceCoPayment.customerContribution(amount);
        return new Commitment(amount.subtract(customerContribution), customerContribution);
    }


    public boolean isRejected() {
        return state == SubmittedClaimState.REJECTED;
    }

    Commitment currentCommitment() {
        return new Commitment(commitment.getCompany(), commitment.getCustomer());
    }

    List<DomainEvent> getDirtyEvents() {
        return ImmutableList.copyOf(dirtyEvents);
    }
}
