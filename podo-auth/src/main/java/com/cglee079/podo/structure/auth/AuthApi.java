package com.cglee079.podo.structure.auth;

import com.cglee079.podo.structure.auth.global.infra.memberapi.MemberApiClient;
import com.cglee079.podo.structure.auth.global.security.TokenAuthenticationService;
import com.cglee079.podo.structure.core.vo.MemberVo;
import com.cglee079.podo.structure.core.vo.TokenValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthApi {

    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    private final MemberApiClient memberApiClient;
    private final TokenAuthenticationService tokenAuthenticationService;

    @PostMapping("/auth/{id}/{password}")
    public void login(@PathVariable("id") String memberId, @PathVariable("password") String password, HttpServletResponse response) {

        final MemberVo member = memberApiClient.getMember(memberId);

        if (StringUtils.isEmpty(member)) {
            return;
        }

        final String savedPassword = new JSONObject(member).getString("password");

        if (!savedPassword.equalsIgnoreCase(password)) {
            return;
        }

        final TokenValue tokenValue = new TokenValue(member.getId(), LocalDateTime.now(), member.getRoles());
        final String token = tokenAuthenticationService.createToken(tokenValue);

        response.setHeader(HEADER_STRING, TOKEN_PREFIX + " " + token);
    }

}
