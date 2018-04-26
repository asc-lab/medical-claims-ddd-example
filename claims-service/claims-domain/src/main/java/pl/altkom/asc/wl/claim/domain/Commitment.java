package pl.altkom.asc.wl.claim.domain;

import java.math.BigDecimal;

import lombok.Value;

import static java.math.BigDecimal.ZERO;

@Value
class Commitment {
    private BigDecimal company;
    private BigDecimal customer;

    static Commitment zero() {
        return new Commitment(ZERO, ZERO);
    }

    static Commitment customerOnly(BigDecimal customer) {
        return new Commitment(ZERO, customer);
    }

    Commitment add(Commitment commitment) {
        return new Commitment(company.add(commitment.getCompany()), customer.add(commitment.getCustomer()));
    }
}
