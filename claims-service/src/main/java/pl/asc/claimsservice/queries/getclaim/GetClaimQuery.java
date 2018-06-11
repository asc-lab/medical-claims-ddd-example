package pl.asc.claimsservice.queries.getclaim;

import lombok.*;
import pl.asc.claimsservice.shared.cqs.Query;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetClaimQuery implements Query<GetClaimResult> {
    private String claimNumber;
}
