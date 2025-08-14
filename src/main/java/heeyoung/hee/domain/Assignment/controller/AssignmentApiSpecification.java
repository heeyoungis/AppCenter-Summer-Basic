package heeyoung.hee.domain.Assignment.controller;

import heeyoung.hee.domain.Assignment.dto.request.AssignmentSubmitDTO;
import heeyoung.hee.domain.Assignment.dto.request.AssignmentUpdateDTO;
import heeyoung.hee.domain.Assignment.dto.response.AssignmentResponseDto;
import heeyoung.hee.domain.Recommendation.dto.response.RecommendationResponseDto;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
            ),
            @ApiResponse(responseCode = "403", description = "❌과제 수정 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorCode.class),
                            examples = {
                                    @ExampleObject(
                                            name = "권한 없음",
                                            value = "{\"error\": \"403\", \"message\": \"과제를 수정할 권한이 없습니다.\"}"
                                    )
                            })
            )

    })
    public ResponseEntity<AssignmentResponseDto> updateAssignment(@Valid @RequestBody AssignmentUpdateDTO assignmentUpdateDTO,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails,
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
            ),
            @ApiResponse(responseCode = "403", description = "❌과제 삭제 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorCode.class),
                            examples = {
                                    @ExampleObject(
                                            name = "권한 없음",
                                            value = "{\"error\": \"403\", \"message\": \"과제를 삭제할 권한이 없습니다.\"}"
                                    )
                            })
            )
    })
    public ResponseEntity<String> deleteAssignment(@PathVariable Long assignmentId,@AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(summary = "과제 조회", description = "모든 과제 조회 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "✅과제 조회 성공")
    })
    public ResponseEntity<List<AssignmentResponseDto>> getAll(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "sort", required = false) String options);

    @Operation(summary = "추천", description = "과제 추천 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "✅추천 성공"),
            @ApiResponse(responseCode = "404", description = "❌추천 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorCode.class,
                                    accessMode = Schema.AccessMode.READ_ONLY),
                            examples = {
                                    @ExampleObject(
                                            name = "과제 찾을 수 없음",
                                            value = "{\"error\": \"404\", \"message\": \"해당 과제를 찾을 수 없습니다.\"}"
                                    )
                            })
            )
    })
    public ResponseEntity<RecommendationResponseDto> doRecommendation(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                      @PathVariable("assignmentId") Long assignmentId);

    @Operation(summary = "추천 취소", description = "과제 추천 취소 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "✅추천 취소 성공"),
            @ApiResponse(responseCode = "404", description = "❌추천 취소 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorCode.class),
                            examples = {
                                    @ExampleObject(
                                            name = "과제 찾을 수 없음",
                                            value = "{\"error\": \"404\", \"message\": \"해당 과제를 찾을 수 없습니다.\"}"
                                    )

                            })
            ),
            @ApiResponse(responseCode = "403", description = "❌추천 취소 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorCode.class),
                            examples = {
                                    @ExampleObject(
                                            name = "권한 없음",
                                            value = "{\"error\": \"403\", \"message\": \"추천을 취소할 권한이 없습니다.\"}"
                                    )

                            })
            )
    })
    public ResponseEntity<String> deleteRecommendation(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                       @PathVariable("assignmentId") Long assignmentId);
}
