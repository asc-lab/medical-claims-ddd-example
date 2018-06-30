package pl.asc.claimsservice.queries.getclaim;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.asc.claimsservice.domainmodel.Claim;
import pl.asc.claimsservice.domainmodel.ClaimRepository;
import pl.asc.claimsservice.domainmodel.Policy;
import pl.asc.claimsservice.domainmodel.PolicyRepository;
import pl.asc.claimsservice.shared.cqs.QueryHandler;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetClaimHandler implements QueryHandler<GetClaimResult, GetClaimQuery> {
    private final ClaimRepository claimRepository;
    private final PolicyRepository policyRepository;

    @Override
    public GetClaimResult handle(GetClaimQuery query) {
        Claim claim = claimRepository.getByNumber(query.getClaimNumber());
        Policy policy = policyRepository.findByNumber(claim.getPolicyVersionRef().getPolicyNumber()).get();

        return new GetClaimResult(
                ClaimDtoAssembler.from(claim, policy).assembleDto()
        );
    }
}
