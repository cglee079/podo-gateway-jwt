package com.cglee079.podo.structure.memberservice;

import com.cglee079.podo.structure.core.vo.MemberVo;
import com.cglee079.podo.structure.memberservice.infra.memberapi.MemberApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberServiceApi {

    private final MemberApiClient memberApiClient;

    @PostMapping("/join")
    public String joinMember(@RequestBody MemberVo memberVo) {
        return memberApiClient.insertMember(memberVo);
    }

}
