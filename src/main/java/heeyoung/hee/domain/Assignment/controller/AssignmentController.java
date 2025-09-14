package heeyoung.hee.domain.Assignment.controller;

import heeyoung.hee.domain.Assignment.dto.request.AssignmentSubmitDTO;
import heeyoung.hee.domain.Assignment.dto.request.AssignmentUpdateDTO;
import heeyoung.hee.domain.Assignment.dto.response.AssignmentResponseDto;
import heeyoung.hee.domain.Assignment.entity.Assignment;
import heeyoung.hee.domain.Assignment.repository.AssignmentRepository;
import heeyoung.hee.domain.Assignment.service.AssignmentService;
import heeyoung.hee.domain.Recommendation.dto.response.RecommendationResponseDto;
import heeyoung.hee.domain.Recommendation.repository.RecommendationRepository;
import heeyoung.hee.domain.Recommendation.service.RecommendationService;
import heeyoung.hee.domain.User.entity.User;
import heeyoung.hee.domain.User.service.UserDetailsImpl;
import heeyoung.hee.global.exception.ErrorCode;
import heeyoung.hee.global.exception.RestApiException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController implements AssignmentApiSpecification {

    private final AssignmentService assignmentService;
    private final RecommendationService recommendationService;
    private final AssignmentRepository assignmentRepository;
    private final RecommendationRepository recommendationRepository;

    // 과제 전체 조회
    @GetMapping
    public ResponseEntity<List<AssignmentResponseDto>> getAll(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "sort", required = false) String options) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        User user = userDetails.getUser();

        return ResponseEntity.status(HttpStatus.OK).body(assignmentService.findAllAssignments(user, options, pageable));
    }

    // 과제 제출
    @PostMapping
    public ResponseEntity<AssignmentResponseDto> submitAssignment(@Valid @RequestBody AssignmentSubmitDTO assignmentSubmitDTO,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        AssignmentResponseDto createdAssignment = assignmentService.submitAssignment(assignmentSubmitDTO, user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(createdAssignment);
    }

    // 과제 수정
    @PutMapping("/{assignmentId}")
    public ResponseEntity<AssignmentResponseDto> updateAssignment(@Valid @RequestBody AssignmentUpdateDTO assignmentUpdateDTO,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                  @PathVariable Long assignmentId) {
        User user = userDetails.getUser();
        AssignmentResponseDto updatedAssignment = assignmentService.updateAssignment(user, assignmentUpdateDTO, assignmentId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedAssignment);
    }

    // 과제 삭제
    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<String> deleteAssignment(@PathVariable Long assignmentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        assignmentService.deleteAssignment(user, assignmentId);
        return ResponseEntity.status(HttpStatus.OK).body("과제가 삭제되었습니다.");
    }

    // 과제 조회
    @GetMapping("/{assignmentId}")
    public ResponseEntity<AssignmentResponseDto> getAssignment(@PathVariable Long assignmentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RestApiException(ErrorCode.ASSIGNMENT_NOT_FOUND));

        // 추천 여부 확인
        boolean isRecommended = recommendationRepository.existsByUserIdAndAssignmentId(user.getId(), assignmentId);

        int count = recommendationRepository.countByAssignmentId(assignmentId);

        AssignmentResponseDto response = AssignmentResponseDto.from(assignment, count, isRecommended);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 추천
    @PostMapping("/{assignmentId}/recommendation")
    public ResponseEntity<RecommendationResponseDto> doRecommendation(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                      @PathVariable("assignmentId") Long assignmentId) {
        User user = userDetails.getUser();
        RecommendationResponseDto recommendationResponseDto = recommendationService.doRecommendation(user.getId(), assignmentId);
        return ResponseEntity.status(HttpStatus.OK).body(recommendationResponseDto);
    }

    // 추천 취소
    @DeleteMapping("/{assignmentId}/recommendation")
    public ResponseEntity<String> deleteRecommendation(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                       @PathVariable("assignmentId") Long assignmentId) {
        User user = userDetails.getUser();
        recommendationService.deleteRecommendation(user.getId(), assignmentId);
        return ResponseEntity.status(HttpStatus.OK).body("추천이 취소되었습니다.");
    }

}
