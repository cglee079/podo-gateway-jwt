package com.cglee079.podo.structure.auth.domain.user.api;

import com.cglee079.podo.structure.auth.domain.user.User;
import com.cglee079.podo.structure.auth.global.security.TokenAuthenticationService;
import com.cglee079.podo.structure.auth.domain.user.repo.UserRepository;
import com.cglee079.podo.structure.auth.domain.user.vo.TokenValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public static final String HEADER_STRING = "Authorization";
    private UserRepository userRepository = new UserRepository();

    @GetMapping("/login/{username}/{password}")
    public void login(@PathVariable("username") String username, @PathVariable("password") String password, HttpServletResponse response) {

        final User user = userRepository.findUserIdByUsernameAndPassword(username, password);

        if (Objects.isNull(user)) {
            return;
        }

        final TokenValue tokenValue = new TokenValue(user, LocalDateTime.now());

        final String token = TokenAuthenticationService.createToken(tokenValue);

        response.setHeader(HEADER_STRING, token);
    }

    @PostMapping("/auth")
    public void auth(HttpServletResponse response) {
        log.info("auth 요청을 받았습니다");

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        final TokenValue principal = (TokenValue) authentication.getPrincipal();

        if (Objects.nonNull(principal)) {
            log.info("사용자 정보 " + principal.toString());
            response.setStatus(HttpStatus.OK.value());
        }

        log.info("사용자 정보를 찾을 수 없습니다.");
    }

}
