package heeyoung.hee.domain.Assignment.controller;

import heeyoung.hee.domain.Assignment.dto.request.AssignmentSubmitDTO;
import heeyoung.hee.domain.Assignment.dto.request.AssignmentUpdateDTO;
import heeyoung.hee.domain.Assignment.dto.response.AssignmentResponseDto;
import heeyoung.hee.domain.User.service.UserDetailsImpl;
import heeyoung.hee.global.exception.ErrorCode;
import heeyoung.hee.global.jwt.TokenResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Assignment", description = "과제 관련 API")
public interface AssignmentApiSpecification {

    @Operation(summary = "과제 제출", description = "과제 제출 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "✅과제 제출 성공"),
            @ApiResponse(responseCode = "400", description = "❌과제 제출 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorCode.class),
                            examples = {
                                    @ExampleObject(
                                            name = "유효성 검사 오류",
                                            value = "{\"error\": \"400\", \"message\": \"유효성 검사에 실패하였습니다.\"}"
                                    )
                            })
            ),
    })
    public ResponseEntity<AssignmentResponseDto> submitAssignment(@RequestBody AssignmentSubmitDTO assignmentSubmitDTO,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(summary = "과제 수정", description = "과제 수정 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "✅과제 수정 성공"),
            @ApiResponse(responseCode = "404", description = "❌과제 수정 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorCode.class),
                            examples = {
                                    @ExampleObject(
                                            name = "과제 찾을 수 없음",
                                            value = "{\"error\": \"404\", \"message\": \"해당 과제를 찾을 수 없습니다.\"}"
                                    )
                            })
            )

    })
    public ResponseEntity<AssignmentResponseDto> updateAssignment(@RequestBody AssignmentUpdateDTO assignmentUpdateDTO,
                                                                  @PathVariable Long assignmentId);

    @Operation(summary = "과제 삭제", description = "과제 삭제 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "✅과제 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "❌과제 삭제 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorCode.class),
                            examples = {
                                    @ExampleObject(
                                            name = "과제 찾을 수 없음",
                                            value = "{\"error\": \"404\", \"message\": \"해당 과제를 찾을 수 없습니다.\"}"
                                    )
                            })
            )
    })
    public ResponseEntity<String> deleteAssignment(@PathVariable Long assignmentId);

    @Operation(summary = "과제 조회", description = "모든 과제 조회 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "✅과제 조회 성공")
    })
    public ResponseEntity<List<AssignmentResponseDto>> getAll(@RequestParam(name = "sort", required = false) String options);
}
