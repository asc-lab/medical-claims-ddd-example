package pl.asc.claimsservice.domainmodel;

import lombok.RequiredArgsConstructor;
import pl.asc.claimsservice.shared.primitives.DateRange;

import java.time.LocalDate;

public enum LimitPeriod {
    POLICY_YEAR {
        @Override
        public DateRange calculateDateRange(LocalDate eventDate, PolicyVersion policy) {
            return new PolicyYears(policy).getPolicyYearContaining(eventDate);
        }
    };

    public abstract DateRange calculateDateRange(LocalDate eventDate, PolicyVersion policy);

    @RequiredArgsConstructor
    static class PolicyYears {
        private final PolicyVersion policy;

        DateRange getPolicyYearContaining(LocalDate theDate) {
            DateRange policyYear = DateRange.between(
                    policy.getCoverPeriod().getFrom(),
                    policy.getCoverPeriod().getFrom().plusYears(1).minusDays(1));

            while(!policyYear.contains(theDate)) {
                policyYear = DateRange.between(
                        policyYear.getTo().plusDays(1),
                        policyYear.getTo().plusYears(1)
                );
            }

            return policyYear;
        }
    }
}
