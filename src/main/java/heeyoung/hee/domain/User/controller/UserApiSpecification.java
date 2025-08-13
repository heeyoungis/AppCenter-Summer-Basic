package heeyoung.hee.domain.User.controller;

import heeyoung.hee.domain.User.dto.request.UserCreateDTO;
import heeyoung.hee.domain.User.dto.request.UserLoginDTO;
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
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.*;

@Tag(name = "User", description = "유저 관련 API")
public interface UserApiSpecification {

    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "회원가입 실패",
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
            @ApiResponse(responseCode = "409", description = "회원가입 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "이미 존재하는 유저",
                                            value = "{\"error\": \"409\", \"message\": \"이미 존재하는 유저입니다\"}"
                                    )
                            }))
    })
    public ResponseEntity<String> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO);

    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "로그인 실패",
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
}

