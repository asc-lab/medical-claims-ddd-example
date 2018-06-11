package pl.asc.claimsservice.domainmodel;

import lombok.RequiredArgsConstructor;
import pl.asc.claimsservice.shared.primitives.DateRange;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
class PolicyVersionCollection {
    private final Policy policy;
    private final Set<PolicyVersion> versions;

    PolicyVersion withNumber(Long number){
        return versions
                .stream()
                .filter(v -> v.getVersionNumber().equals(number))
                .findFirst()
                .get(); //change to or else throw
    }

    PolicyVersion validAtDate(LocalDate theDate) {
        Optional<PolicyVersion> version = versions
                .stream()
                .sorted(Comparators.BY_VERSION_NUMBER_DESC)
                .filter(v -> v.isValidAt(theDate))
                .findFirst();

        return version.isPresent() ? version.get() : lastVersion();
    }

    PolicyVersion lastVersion() {
        return versions
                .stream()
                .sorted(Comparators.BY_VERSION_NUMBER_DESC)
                .findFirst()
                .get();
    }


    PolicyVersion add(
            Long versionNumber,
            String productCode,
            Person policyHolder,
            String accountNumber,
            DateRange coverPeriod,
            DateRange versionPeriod
            ) {
        PolicyVersion ver = new PolicyVersion(
                null,
                policy,
                versionNumber,
                productCode,
                policyHolder,
                accountNumber,
                coverPeriod,
                versionPeriod,
                new HashSet<>()
        );
        versions.add(ver);
        return ver;
    }


    static class Comparators {
        static final Comparator<PolicyVersion> BY_VERSION_NUMBER_ASC = (v1,v2) -> v1.getVersionNumber().compareTo(v2.getVersionNumber());
        static final Comparator<PolicyVersion> BY_VERSION_NUMBER_DESC = (v1,v2) -> v2.getVersionNumber().compareTo(v1.getVersionNumber());
    }
}
