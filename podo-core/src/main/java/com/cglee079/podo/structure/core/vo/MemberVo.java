package com.cglee079.podo.structure.core.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class MemberVo {

    private String id;
    private String password;
    private String name;
    private List<String> roles;

    public MemberVo(String id, String password, String name, List<String> roles) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }

}
