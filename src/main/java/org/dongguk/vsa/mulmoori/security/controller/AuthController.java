package org.dongguk.vsa.mulmoori.security.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.annotation.security.AccountID;
import org.dongguk.vsa.mulmoori.core.contants.Constants;
import org.dongguk.vsa.mulmoori.core.dto.ResponseDto;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;
import org.dongguk.vsa.mulmoori.core.exception.type.CommonException;
import org.dongguk.vsa.mulmoori.core.utility.CookieUtil;
import org.dongguk.vsa.mulmoori.core.utility.HeaderUtil;
import org.dongguk.vsa.mulmoori.security.dto.request.SignUpByDefaultRequestDto;
import org.dongguk.vsa.mulmoori.security.dto.request.ValidateAuthenticationCodeRequestDto;
import org.dongguk.vsa.mulmoori.security.dto.request.ValidateEmailRequestDto;
import org.dongguk.vsa.mulmoori.security.dto.response.DefaultJsonWebTokenDto;
import org.dongguk.vsa.mulmoori.security.usecase.*;
import org.dongguk.vsa.mulmoori.security.utility.HttpServletUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Hidden
public class AuthController {

    private final SignUpByDefaultUseCase signUpByDefaultUseCase;
    private final WithdrawalUseCase withdrawalUseCase;
    private final ReissuePasswordUseCase reissuePasswordUseCase;
    private final ReissueJsonWebTokenUseCase reissueJsonWebTokenUseCase;

    private final ValidateEmailUseCase validateEmailUseCase;
    private final ValidateAuthenticationCodeUseCase validateAuthenticationCodeUseCase;

    private final HttpServletUtil httpServletUtil;

    @PostMapping("/sign-up")
    public void signUp(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody @Valid SignUpByDefaultRequestDto requestDto
    ) throws IOException {
        String temporaryToken = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER_ERROR));

        DefaultJsonWebTokenDto tokenDto = signUpByDefaultUseCase.execute(temporaryToken, requestDto);

        httpServletUtil.onSuccessBodyResponseWithJWTCookie(response, tokenDto);
    }

    @PostMapping("/withdrawal")
    public ResponseDto<?> withdrawal(
            HttpServletRequest request,
            HttpServletResponse response,
            @AccountID UUID accountId
    ) {
        // 회원 탈퇴
        withdrawalUseCase.execute(accountId);

        // User-Agent 헤더를 통해 요청이 브라우저에서 온 것인지 확인
        String userAgent = request.getHeader("User-Agent");

        // 브라우저에서 온 요청인 경우 쿠키를 삭제함
        if (userAgent != null && userAgent.contains("Mozilla")) {
            CookieUtil.deleteCookie(request, response, Constants.ACCESS_TOKEN);
            CookieUtil.deleteCookie(request, response, Constants.REFRESH_TOKEN);
            CookieUtil.deleteCookie(request, response, "JSESSIONID");
        }

        return ResponseDto.noContent();
    }

    @PostMapping("/reissue/password")
    public ResponseDto<?> reissuePassword(
            HttpServletRequest request
    ) {
        String temporaryToken = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER_ERROR));

        reissuePasswordUseCase.execute(temporaryToken);

        return ResponseDto.ok(null);
    }

    @PostMapping("/reissue/token")
    public void reissueDefaultJsonWebToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String refreshToken = CookieUtil.refineCookie(request, Constants.REFRESH_TOKEN)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER_ERROR));

        DefaultJsonWebTokenDto tokenDto = reissueJsonWebTokenUseCase.execute(refreshToken);

        httpServletUtil.onSuccessBodyResponseWithJWTCookie(response, tokenDto);
    }

    @PostMapping("/validations/email")
    public ResponseDto<?> validateEmail(
            @RequestBody @Valid ValidateEmailRequestDto requestDto
    ) {
        return ResponseDto.ok(validateEmailUseCase.execute(requestDto));
    }

    @PostMapping("/validations/authentication-code")
    public ResponseDto<?> validateAuthenticationCode(
            @RequestBody @Valid ValidateAuthenticationCodeRequestDto requestDto
    ) {
        return ResponseDto.created(validateAuthenticationCodeUseCase.execute(requestDto));
    }
}

