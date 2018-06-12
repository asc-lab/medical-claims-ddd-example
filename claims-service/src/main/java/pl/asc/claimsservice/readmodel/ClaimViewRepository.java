package pl.asc.claimsservice.readmodel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimViewRepository extends JpaRepository<ClaimView, Long> {
}
