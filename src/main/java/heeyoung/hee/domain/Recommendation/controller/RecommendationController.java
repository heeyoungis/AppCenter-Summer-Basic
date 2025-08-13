package heeyoung.hee.domain.Recommendation.controller;

import heeyoung.hee.domain.Recommendation.dto.request.RecommendationRequestDto;
import heeyoung.hee.domain.Recommendation.dto.response.RecommendationResponseDto;
import heeyoung.hee.domain.Recommendation.service.RecommendationService;
import heeyoung.hee.domain.User.entity.User;
import heeyoung.hee.domain.User.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignments/{assignmentId}/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    // 추천
    @PostMapping("/")
    public ResponseEntity<RecommendationResponseDto> doRecommendation(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                      @RequestBody RecommendationRequestDto requestDto) {
        User user = userDetails.getUser();
        RecommendationResponseDto recommendationResponseDto = recommendationService.doRecommendation(user, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(recommendationResponseDto);
    }

    // 삭제
    @DeleteMapping("/")
    public ResponseEntity<String> deleteRecommendation(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                       @RequestBody RecommendationRequestDto requestDto) {
        User user = userDetails.getUser();
        recommendationService.deleteRecommendation(user, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body("추천이 취소되었습니다.");
    }

}
