package pl.asc.claimsservice.queries.getclaim;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.asc.claimsservice.domainmodel.Claim;
import pl.asc.claimsservice.domainmodel.ClaimRepository;
import pl.asc.claimsservice.shared.cqs.QueryHandler;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetClaimHandler implements QueryHandler<GetClaimResult, GetClaimQuery> {
    private final ClaimRepository claimRepository;
    @Override
    public GetClaimResult handle(GetClaimQuery query) {
        Claim claim = claimRepository.getByNumber(query.getClaimNumber());

        return new GetClaimResult(
                ClaimDtoAssembler.from(claim).assembleDto()
        );
    }
}
