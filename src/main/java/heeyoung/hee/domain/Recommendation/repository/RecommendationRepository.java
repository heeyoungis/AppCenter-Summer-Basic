package heeyoung.hee.domain.Recommendation.repository;

import heeyoung.hee.domain.Assignment.dto.response.AssignmentResponseDto;
import heeyoung.hee.domain.Recommendation.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    int countByAssignmentId(Long assignmentId);
    Optional<Recommendation> findByUserIdAndAssignmentId(Long id, Long assignmentId);
}
