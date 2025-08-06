package heeyoung.hee.domain.Assignment.service;

import heeyoung.hee.domain.Assignment.dto.request.AssignmentSubmitDTO;
import heeyoung.hee.domain.Assignment.dto.request.AssignmentUpdateDTO;
import heeyoung.hee.domain.Assignment.dto.response.AssignmentResponseDto;
import heeyoung.hee.domain.Assignment.entity.Assignment;
import heeyoung.hee.domain.Assignment.repository.AssignmentRepository;
import heeyoung.hee.domain.User.entity.User;
import heeyoung.hee.domain.User.repository.UserRepository;
import heeyoung.hee.global.exception.ErrorCode;
import heeyoung.hee.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;

    // 과제 제출
    public AssignmentResponseDto submitAssignment(AssignmentSubmitDTO dto, Long userId) {

        // 유저 등록
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(ErrorCode.USER_NOT_FOUND));

        // 과제 객체 생성
        Assignment assignment = Assignment.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .link(dto.getLink())
                .createdAt(new Date().toString())
                .user(user)
                .build();

        // DB 에 저장
        assignmentRepository.save(assignment);

        // 응답DTO 에 담아서 반환
        return AssignmentResponseDto.from(assignment);
    }

    // 과제 수정
    public AssignmentResponseDto updateAssignment(AssignmentUpdateDTO dto, Long assignmentId) {

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new RestApiException(ErrorCode.ASSIGNMENT_NOT_FOUND));

        // Assignment 갱신
        Assignment updatedAssignment = assignment.update(dto.getTitle(), dto.getContent(), dto.getLink());

        // 갱신된 정보 DB 에 저장
        assignmentRepository.save(updatedAssignment);

        // 응답DTO 에 담아서 반환
        return AssignmentResponseDto.from(updatedAssignment);
    }

    // 과제 삭제
    public void deleteAssignment(Long assignmentId) {

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new RestApiException(ErrorCode.ASSIGNMENT_NOT_FOUND));

        assignmentRepository.delete(assignment);
    }

    // 과제 전체 조회
    public List<AssignmentResponseDto> findAllAssignments(String option, String direction) {
        List<Assignment> assignments = List.of();

        // 오름차순
        if (direction.equalsIgnoreCase("asc")) {
            assignments = assignmentRepository.findAll(Sort.by(Sort.Direction.ASC, option));
        }

        // 내림차순
        else if (direction.equalsIgnoreCase("desc")) {
            assignments = assignmentRepository.findAll(Sort.by(Sort.Direction.DESC, option));
        }

        return assignments.stream()
                .map(AssignmentResponseDto::from)
                .toList();
    }

}
