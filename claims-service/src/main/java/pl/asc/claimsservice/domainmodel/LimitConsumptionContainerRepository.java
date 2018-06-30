package pl.asc.claimsservice.domainmodel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LimitConsumptionContainerRepository extends JpaRepository<LimitConsumptionContainer, Long> {
    List<LimitConsumptionContainer> findByPolicyRefPolicyNumberAndServiceCodeCodeIn(String policyNumber,List<String> codes);

    default LimitConsumptionContainerCollection findForPolicyAndServices(String policyNumber,List<String> codes){
        return new LimitConsumptionContainerCollection(findByPolicyRefPolicyNumberAndServiceCodeCodeIn(policyNumber, codes));
    }

    default void save(LimitConsumptionContainerCollection consumptions) {
        save(consumptions.getConsumptionContainers());
    }
}
