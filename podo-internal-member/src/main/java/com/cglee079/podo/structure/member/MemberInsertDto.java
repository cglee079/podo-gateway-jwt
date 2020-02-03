package com.cglee079.podo.structure.member;

import lombok.Getter;

@Getter
public class MemberInsertDto {

    private String id;
    private String password;
    private String name;

    public MemberInsertDto(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }
}
