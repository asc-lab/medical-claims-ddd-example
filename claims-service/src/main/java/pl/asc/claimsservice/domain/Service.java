package pl.asc.claimsservice.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Service {
    @Id
    @GeneratedValue
    private Long id;

    private String code;

    @ManyToOne
    @JoinColumn(name = "COVER_ID")
    private Cover cover;

    private Limit limit;

    private CoPayment coPayment;
}
