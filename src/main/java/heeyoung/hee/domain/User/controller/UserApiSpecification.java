package heeyoung.hee.domain.User.controller;

import heeyoung.hee.domain.Assignment.dto.response.AssignmentResponseDto;
import heeyoung.hee.domain.User.dto.request.UserCreateDTO;
import heeyoung.hee.domain.User.dto.request.UserLoginDTO;
import heeyoung.hee.domain.User.dto.request.UserUpdateDto;
import heeyoung.hee.domain.User.dto.response.UserInfoResponseDto;
import heeyoung.hee.domain.User.dto.response.UserResponseDTO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.*;
import java.util.List;

@Tag(name = "User", description = "유저 관련 API")
public interface UserApiSpecification {

    @Operation(summary = "회원가입", description = "회원가입 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "✅회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "❌회원가입 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorCode.class),
                            examples = {
                                    @ExampleObject(
                                            name = "이메일 형식 오류",
                                            value = "{\"error\": \"400\", \"message\": \"이메일 형식이 올바르지 않습니다.\"}"
                                    ),
                                    @ExampleObject(
                                            name = "비밀번호 형식 오류",
                                            value = "{\"error\": \"400\", \"message\": \"비밀번호 형식이 올바르지 않습니다.\"}"
                                    )
                            })
            ),
            @ApiResponse(responseCode = "409", description = "❌회원가입 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "이미 존재하는 유저",
                                            value = "{\"error\": \"409\", \"message\": \"이미 존재하는 유저입니다\"}"
                                    )
                            }))
    })
    public ResponseEntity<String> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO);

    @Operation(summary = "로그인", description = "로그인 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "✅로그인 성공"),
            @ApiResponse(responseCode = "400", description = "❌로그인 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorCode.class),
                            examples = {
                                    @ExampleObject(
                                            name = "비밀번호 불일치",
                                            value = "{\"error\": \"400\", \"message\": \"비밀번호가 올바르지 않습니다.\"}"
                                    )
                            })
            )
    })
    public ResponseEntity<TokenResponseDto> login(@RequestBody UserLoginDTO userLoginDTO);


    @Operation(summary = "로그아웃", description = "로그아웃 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "✅로그아웃 성공"),
            @ApiResponse(responseCode = "404", description = "❌로그아웃 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorCode.class),
                            examples = {
                                    @ExampleObject(
                                            name = "일치하는 유저 없음",
                                            value = "{\"error\": \"404\", \"message\": \"유저 정보를 찾을 수 없습니다.\"}"
                                    )
                            })
            )
    })
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(summary = "유저 조회", description = "마이 페이지 조회 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "✅유저 조회 성공"),
            @ApiResponse(responseCode = "404", description = "❌유저 조회 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorCode.class),
                            examples = {
                                    @ExampleObject(
                                            name = "일치하는 유저 없음",
                                            value = "{\"error\": \"404\", \"message\": \"유저 정보를 찾을 수 없습니다.\"}"
                                    )
                            })
            )
    })
    public ResponseEntity<UserInfoResponseDto> getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(summary = "유저 정보 수정", description = "유저 정보 수정 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "✅유저 정보 수정 성공"),
            @ApiResponse(responseCode = "404", description = "❌유저 정보 수정 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorCode.class),
                            examples = {
                                    @ExampleObject(
                                            name = "일치하는 유저 없음",
                                            value = "{\"error\": \"404\", \"message\": \"유저 정보를 찾을 수 없습니다.\"}"
                                    )
                            })
            )
    })
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(summary = "유저 탈퇴", description = "유저 탈퇴 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "✅유저 탈퇴 성공"),
            @ApiResponse(responseCode = "404", description = "❌유저 탈퇴 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorCode.class),
                            examples = {
                                    @ExampleObject(
                                            name = "일치하는 유저 없음",
                                            value = "{\"error\": \"404\", \"message\": \"유저 정보를 찾을 수 없습니다.\"}"
                                    )
                            })
            )
    })
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(summary = "유저 전체 조회", description = "모든 유저 조회 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "✅유저 전체 조회 성공")
    })
    public ResponseEntity<List<UserResponseDTO>> getAllUsers();

    @Operation(summary = "파트 별 조회", description = "파트 별 유저 조회 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "✅파트 별 조회 성공")
    })
    public ResponseEntity<List<UserResponseDTO>> getPartUsers(@PathVariable String parts);
}

