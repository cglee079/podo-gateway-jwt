package com.cglee079.podo.structure.auth;

import com.cglee079.podo.structure.core.vo.MemberVo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthApi {

    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    private final AuthService authService;

    @PostMapping("/login")
    public TokenValue login(@RequestBody MemberVo member, HttpServletResponse response) {

        final TokenValue tokenValue = authService.authenticate(member.getId(), member.getPassword());

        if (Objects.isNull(tokenValue)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

        return tokenValue;
    }

    @PostMapping("/refresh")
    public TokenValue refresh(RefreshTokenDto refreshTokenDto, HttpServletResponse response) {

        final TokenValue tokenValue = authService.refresh(refreshTokenDto);

        if (Objects.isNull(tokenValue)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

        return tokenValue;
    }

    @PostMapping("/logout")
    public void refresh(HttpServletRequest request) {
        final String accessToken = request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX, "").trim();
        authService.logout(accessToken);
    }

}
