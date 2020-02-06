package com.cglee079.podo.structure.gateway.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum  ResponseCode {

    EXPIRE_TOKEN("444");

    private final String code;


}
