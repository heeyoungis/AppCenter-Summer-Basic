package heeyoung.hee.domain.Recommendation.repository;

import heeyoung.hee.domain.Recommendation.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    Optional<Recommendation> findByUserIdAndAssignmentId(Long id, Long assignmentId);
}
