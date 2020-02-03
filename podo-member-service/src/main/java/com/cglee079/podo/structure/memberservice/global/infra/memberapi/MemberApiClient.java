package com.cglee079.podo.structure.memberservice.global.infra.memberapi;

import com.cglee079.podo.structure.core.vo.MemberVo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MemberApiClient {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String MEMBER_SERVICE_API = "http://127.0.0.1:6060";

    public String insertMember(MemberVo memberVo){
        final HttpEntity<MemberVo> httpEntity = new HttpEntity<>(memberVo, new HttpHeaders());

        final ResponseEntity<String> exchange = REST_TEMPLATE.exchange(MEMBER_SERVICE_API + "/api/members", HttpMethod.POST, httpEntity, String.class);

        return exchange.getBody();
    }
}

