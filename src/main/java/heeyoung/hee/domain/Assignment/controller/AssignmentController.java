package heeyoung.hee.domain.Assignment.controller;

import heeyoung.hee.domain.Assignment.dto.request.AssignmentSubmitDTO;
import heeyoung.hee.domain.Assignment.dto.request.AssignmentUpdateDTO;
import heeyoung.hee.domain.Assignment.dto.response.AssignmentResponseDto;
import heeyoung.hee.domain.Assignment.entity.Assignment;
import heeyoung.hee.domain.Assignment.service.AssignmentService;
import heeyoung.hee.domain.User.entity.User;
import heeyoung.hee.domain.User.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignment/")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    // 과제 전체 조회
    @GetMapping
    public ResponseEntity<List<AssignmentResponseDto>> getAll(@RequestParam(name = "sort", required = false) String options) {
        String[] sortParams = {"createdAt", "asc"}; // 기본값 설정
        if (options != null) {
            sortParams = options.split(",");
        }
        return ResponseEntity.status(HttpStatus.OK).body(assignmentService.findAllAssignments(sortParams[0], sortParams[1]));
    }

    // 과제 제출
    @PostMapping
    public ResponseEntity<AssignmentResponseDto> submitAssignment(@RequestBody AssignmentSubmitDTO assignmentSubmitDTO,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        AssignmentResponseDto createdAssignment = assignmentService.submitAssignment(assignmentSubmitDTO, user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(createdAssignment);
    }

    // 과제 수정
    @PatchMapping("/{assignmentId}")
    public ResponseEntity<AssignmentResponseDto> updateAssignment(@RequestBody AssignmentUpdateDTO assignmentUpdateDTO,
                                                                  @PathVariable Long assignmentId) {
        AssignmentResponseDto updatedAssignment = assignmentService.updateAssignment(assignmentUpdateDTO, assignmentId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedAssignment);
    }

    // 과제 삭제
    @PostMapping("/{assignmentId}")
    public ResponseEntity<String> deleteAssignment(@PathVariable Long assignmentId) {
        assignmentService.deleteAssignment(assignmentId);
        return ResponseEntity.status(HttpStatus.OK).body("과제가 삭제되었습니다.");
    }

}
