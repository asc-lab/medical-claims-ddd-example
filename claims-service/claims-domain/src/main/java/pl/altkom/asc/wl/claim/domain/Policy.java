package pl.altkom.asc.wl.claim.domain;

import java.util.List;

import lombok.Value;

@Value
class Policy {

    private String number;

    private List<PolicyVersion> versions;

    public PolicyVersion lastVersion() {
        //fake implementation
        return versions.iterator().next();
    }
}
