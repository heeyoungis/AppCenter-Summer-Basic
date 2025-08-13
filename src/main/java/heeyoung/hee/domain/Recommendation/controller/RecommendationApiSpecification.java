package heeyoung.hee.domain.Recommendation.controller;

import heeyoung.hee.domain.Recommendation.dto.response.RecommendationResponseDto;
import heeyoung.hee.domain.User.service.UserDetailsImpl;
import heeyoung.hee.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Recommendation", description = "추천 관련 API")
public interface RecommendationApiSpecification {

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
