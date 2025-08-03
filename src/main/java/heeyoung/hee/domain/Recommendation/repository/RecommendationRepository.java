package heeyoung.hee.domain.Recommendation.repository;

import heeyoung.hee.domain.Recommendation.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
}
