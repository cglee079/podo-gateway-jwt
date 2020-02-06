package com.cglee079.podo.structure.core.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@ToString
public class JwtValue {
    private List<String> roles = new ArrayList<>();
    private String createTime;

    public JwtValue(List<String> roles, LocalDateTime dateTime) {
        assert dateTime != null;
        assert roles != null;

        this.roles.addAll(roles);
        this.createTime = dateTime.toString();
    }

    public List<String> getRoles() {
        return new ArrayList<>(this.roles);
    }
}
