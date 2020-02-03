package com.cglee079.podo.structure.auth.global.infra.memberapi;

import com.cglee079.podo.structure.core.vo.MemberVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class MemberApiClient {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String MEMBER_SERVICE_API = "http://localhost:6060";

    public MemberVo getMember(String memberId) {
        final HttpEntity<Object> httpEntity = new HttpEntity<>(new HttpHeaders());
        final ResponseEntity<String> exchange = REST_TEMPLATE.exchange(MEMBER_SERVICE_API + "/api/members/" + memberId, HttpMethod.GET, httpEntity, String.class);

        try {
            return new ObjectMapper().readValue(Objects.requireNonNull(exchange.getBody()), MemberVo.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
