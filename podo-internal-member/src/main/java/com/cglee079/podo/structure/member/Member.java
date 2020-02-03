package com.cglee079.podo.structure.member;

import com.cglee079.podo.structure.core.vo.MemberVo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class Member {

    private Long seq;
    private String id;
    private String password;
    private String name;
    private List<String> roles = new ArrayList<>();

    public Member(MemberVo memberVo) {
        this.id = memberVo.getId();
        this.password = memberVo.getPassword();
        this.name = memberVo.getName();
    }

    public Member(Long seq, String id, String password, String name) {
        this.seq = seq;
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public void addRole(String role) {
        this.roles.add(role);
    }
}
