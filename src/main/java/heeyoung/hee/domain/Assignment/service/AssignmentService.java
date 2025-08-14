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
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;

    // 과제 제출
    @Transactional
    public AssignmentResponseDto submitAssignment(AssignmentSubmitDTO dto, Long userId) {

        // 유저 조회
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
    @Transactional
    public AssignmentResponseDto updateAssignment(User user, AssignmentUpdateDTO dto, Long assignmentId) {

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new RestApiException(ErrorCode.ASSIGNMENT_NOT_FOUND));

        // 유저 검증
        if (!assignment.getUser().getId().equals(user.getId())) {
            throw new RestApiException(ErrorCode.USER_NOT_MATCH);
        }

        // Assignment 갱신
        Assignment updatedAssignment = assignment.update(dto.getTitle(), dto.getContent(), dto.getLink());

        // 갱신된 정보 DB 에 저장
        assignmentRepository.save(updatedAssignment);

        // 응답DTO 에 담아서 반환
        return AssignmentResponseDto.from(updatedAssignment);
    }

    // 과제 삭제
    @Transactional
    public void deleteAssignment(User user, Long assignmentId) {

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new RestApiException(ErrorCode.ASSIGNMENT_NOT_FOUND));

        // 유저 검증
        if (!assignment.getUser().getId().equals(user.getId())) {
            throw new RestApiException(ErrorCode.USER_NOT_MATCH);
        }

        assignmentRepository.delete(assignment);
    }

    // 과제 전체 조회
    @Transactional(readOnly = true)
    public List<AssignmentResponseDto> findAllAssignments(String option, Pageable pageable) {

        List<Assignment> assignments = new ArrayList<>();

        if (option != null) {
            // 추천순
            if (option.equalsIgnoreCase("recommendation")) {
                List<Assignment> allAssignments = assignmentRepository.findAll();
                allAssignments.sort(
                        Comparator.comparingInt((Assignment a) -> a.getRecommendations().size())
                                .reversed()
                );
                // 수동 페이징
                int start = pageable.getPageNumber() * pageable.getPageSize();
                int end = Math.min(start + pageable.getPageSize(), allAssignments.size());
                assignments = allAssignments.subList(start, end);

            }
            // 최신순
            else if (option.equalsIgnoreCase("createdAt")) {
                assignments = assignmentRepository.findAll(pageable).getContent();
            }
            else {
                throw new RestApiException(ErrorCode.INVALID_SORT_OPTION);
            }
        } else {
            assignments = assignmentRepository.findAll(pageable).getContent();
        }

        return assignments.stream()
                .map(AssignmentResponseDto::from)
                .toList();
    }

}
