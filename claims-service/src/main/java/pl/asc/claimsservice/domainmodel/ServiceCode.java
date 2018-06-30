package pl.asc.claimsservice.domainmodel;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ServiceCode {
    private String code;

    static ServiceCode of(String code) {
        return new ServiceCode(code);
    }
}
