package pl.asc.claimsservice.domainmodel;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class ClaimStatusMachine {

    private static final Map<ClaimStatus, Set<ClaimStatus>> AVAILABLE_STATUS_CHANGES = defineChanges();

    static boolean canChange(Claim claim, ClaimStatus newStatus) {
        ClaimStatus actualStatus = claim.getStatus();
        return isNotAvailable(actualStatus, newStatus);
    }

    private static boolean isNotAvailable(ClaimStatus actualStatus, ClaimStatus newStatus) {
        Set<ClaimStatus> availableChanges = AVAILABLE_STATUS_CHANGES.get(actualStatus);
        return availableChanges.contains(newStatus);
    }

    private static Map<ClaimStatus, Set<ClaimStatus>> defineChanges() {
        Map<ClaimStatus, Set<ClaimStatus>> changes = new HashMap<>();

        changes.put(ClaimStatus.IN_EVALUATION, new HashSet<>(Arrays.asList(ClaimStatus.EVALUATED)));
        changes.put(ClaimStatus.EVALUATED, new HashSet<>(Arrays.asList(ClaimStatus.ACCEPTED, ClaimStatus.REJECTED)));
        changes.put(ClaimStatus.ACCEPTED, new HashSet<>(Arrays.asList(ClaimStatus.REJECTED)));
        changes.put(ClaimStatus.REJECTED, new HashSet<>());

        return Collections.unmodifiableMap(changes);
    }
}
