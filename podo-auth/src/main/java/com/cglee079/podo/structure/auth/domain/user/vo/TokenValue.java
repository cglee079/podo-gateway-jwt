package com.cglee079.podo.structure.auth.domain.user.vo;

import com.cglee079.podo.structure.auth.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class TokenValue {
    private String userId;
    private String username;
    private String role;
    private String createTime;

    public TokenValue(User user, LocalDateTime dateTime) {
        this.username = user.getUsername();
        this.userId = user.getUserId();
        this.role = user.getRole();
        this.createTime = dateTime.toString();
    }
}
