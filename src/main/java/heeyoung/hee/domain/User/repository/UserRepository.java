package heeyoung.hee.domain.User.repository;

import heeyoung.hee.domain.Assignment.dto.response.AssignmentResponseDto;
import heeyoung.hee.domain.User.dto.response.UserResponseDTO;
import heeyoung.hee.domain.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByPart(String part);

    @Query(""" 
            SELECT new heeyoung.hee.domain.User.dto.response.UserResponseDTO(
                    u.email, u.name, u.part, u.gen, u.phoneNumber)
            FROM User u
        """)
    List<UserResponseDTO> findAllUsers();

    @Query("""
        SELECT new heeyoung.hee.domain.User.dto.response.UserResponseDTO(
            u.email, u.name, u.part, u.gen, u.phoneNumber)
        FROM User u
        WHERE u.part = :part
    """)
    List<UserResponseDTO> findAllByPart(@Param("part") String part);
}
