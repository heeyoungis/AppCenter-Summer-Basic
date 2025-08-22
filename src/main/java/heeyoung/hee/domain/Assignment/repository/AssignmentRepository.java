package heeyoung.hee.domain.Assignment.repository;

import heeyoung.hee.domain.Assignment.dto.response.AssignmentResponseDto;
import heeyoung.hee.domain.Assignment.entity.Assignment;
import heeyoung.hee.domain.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    @Query("""
        SELECT new heeyoung.hee.domain.Assignment.dto.response.AssignmentResponseDto(
            a.id, a.title, a.content, a.link, a.createdAt)
        FROM Assignment a
        WHERE a.user.id = :userId
        """)
    List<AssignmentResponseDto> findUserAssignments(@Param("userId") Long userId);
}
