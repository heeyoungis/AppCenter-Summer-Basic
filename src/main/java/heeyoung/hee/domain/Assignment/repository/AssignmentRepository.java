package heeyoung.hee.domain.Assignment.repository;

import heeyoung.hee.domain.Assignment.dto.response.UserAssignmentResponseDto;
import heeyoung.hee.domain.Assignment.entity.Assignment;
import heeyoung.hee.domain.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    @Query("""
        SELECT new heeyoung.hee.domain.Assignment.dto.response.UserAssignmentResponseDto(
            a.id, a.title, a.content, a.link, a.createdAt, a.recommendationCount)
        FROM Assignment a
        WHERE a.userId = :userId
        """)
    List<UserAssignmentResponseDto> findUserAssignments(@Param("userId") Long userId);
}
