package pl.asc.claimsservice.domain;

import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class CoverCollection {
    private final PolicyVersion policyVersion;
    private final Set<Cover> covers;

    Cover add(String code) {
        Cover cover = new Cover(code);
        covers.add(cover);
        return cover;
    }
}
