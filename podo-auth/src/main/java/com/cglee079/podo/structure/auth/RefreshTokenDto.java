package com.cglee079.podo.structure.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenDto {

    private String grantType;
    private String refreshToken;

}
