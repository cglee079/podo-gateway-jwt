package com.cglee079.podo.structure.auth.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class User {

    private String userId;
    private String username;
    private String role;

    public User(String userId, String username, String role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }
}
