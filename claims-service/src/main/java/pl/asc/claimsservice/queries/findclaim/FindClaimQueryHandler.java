package pl.asc.claimsservice.queries.findclaim;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.asc.claimsservice.readmodel.ClaimView;
import pl.asc.claimsservice.readmodel.ClaimViewRepository;
import pl.asc.claimsservice.shared.cqs.QueryHandler;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindClaimQueryHandler implements QueryHandler<FindClaimQueryResult, FindClaimQuery> {
    private final ClaimViewRepository claimViewRepository;

    @Override
    public FindClaimQueryResult handle(FindClaimQuery query) {
        List<ClaimView> claims = claimViewRepository.findAll();

        return new FindClaimQueryResult(
                claims.stream().map(c->ClaimViewDtoAssembler.from(c).assembleDto()).collect(Collectors.toList())
        );
    }


}
