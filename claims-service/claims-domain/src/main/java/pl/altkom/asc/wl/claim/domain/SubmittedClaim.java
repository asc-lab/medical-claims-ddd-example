package pl.altkom.asc.wl.claim.domain;

import java.time.LocalDateTime;

import lombok.Value;
import lombok.experimental.NonFinal;

@Value
class SubmittedClaim {

    private String policyNumber;
    private LocalDateTime incidentTime;
    @NonFinal
    private SubmittedClaimState state;

    SubmittedClaim(Policy policy, LocalDateTime incidentTime) {
        this.policyNumber = policy.getNumber();
        this.incidentTime = incidentTime;

        PolicyVersion policyVersion = policy.lastVersion();
        if (!policyVersion.underProtection(incidentTime)) {
            rejectClaimForOutOfProtection();
        } else {
            this.state = SubmittedClaimState.PRELIMINARY_ACCEPTED;
        }
    }

    private void rejectClaimForOutOfProtection() {
        this.state = SubmittedClaimState.REJECTED;
    }


    public boolean isRejected() {
        return state == SubmittedClaimState.REJECTED;
    }
}
