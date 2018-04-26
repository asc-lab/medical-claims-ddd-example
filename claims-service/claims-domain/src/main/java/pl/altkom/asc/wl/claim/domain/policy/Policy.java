package pl.altkom.asc.wl.claim.domain.policy;

import java.util.List;

import lombok.Value;

@Value
public class Policy {

    private List<PolicyVersion> versions;

    public PolicyVersion lastVersion() {
        //fake implementation
        return versions.iterator().next();
    }
}
