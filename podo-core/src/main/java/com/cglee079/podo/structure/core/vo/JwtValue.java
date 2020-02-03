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
    private String id;
    private List<String> roles = new ArrayList<>();
    private String createTime;

    public JwtValue(String id, List<String> roles, LocalDateTime dateTime) {
        assert id != null;
        assert dateTime != null;
        assert roles != null;

        this.id = id;
        this.roles.addAll(roles);
        this.createTime = dateTime.toString();
    }

    public List<String> getRoles() {
        return new ArrayList<>(this.roles);
    }
}
