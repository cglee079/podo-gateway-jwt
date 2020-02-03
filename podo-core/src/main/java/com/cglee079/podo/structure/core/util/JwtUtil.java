package com.cglee079.podo.structure.core.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.json.JSONObject;

public class JwtUtil {

    public static JSONObject getJwtBody(String token) {
        final int i = token.lastIndexOf('.');
        final String withoutSignature = token.substring(0, i + 1);
        final Jwt<Header, Claims> untrusted = Jwts.parser().parseClaimsJwt(withoutSignature);

        final String subject = untrusted.getBody().getSubject();

        return new JSONObject(subject);
    }

}
