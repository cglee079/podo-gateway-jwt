package com.cglee079.podo.structure.auth.domain.user.repo;

import com.cglee079.podo.structure.auth.domain.user.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private static Map<String, String> USER_AUTH;
    private static Map<String, User> USERS;

    static {
        final String USERNAME_01 = "user";
        final String USERNAME_02 = "abc";

        USER_AUTH = new HashMap<>();
        USER_AUTH.put(USERNAME_01, "pwd");
        USER_AUTH.put(USERNAME_02, "123");

        USERS = new HashMap<>();
        USERS.put(USERNAME_01, new User(USERNAME_01, "changoo", "USER"));
        USERS.put(USERNAME_02, new User(USERNAME_02, "junwoo", null));
    }

    public User findUserIdByUsernameAndPassword(String username, String password) {
        if (USERS.containsKey(username) && USER_AUTH.get(username).equalsIgnoreCase(password)) {
            return USERS.get(username);
        }

        return null;
    }

}
