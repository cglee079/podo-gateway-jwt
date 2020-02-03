package com.cglee079.podo.structure.memberservice;

import com.cglee079.podo.structure.core.vo.MemberVo;
import com.cglee079.podo.structure.memberservice.global.infra.memberapi.MemberApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class MemberServiceApi {

    private final MemberApiClient memberApiClient;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public String joinMember(@RequestBody MemberVo memberVo) {
        return memberApiClient.insertMember(encodePassword(memberVo));
    }

    private MemberVo encodePassword(@RequestBody MemberVo memberVo) {
        return new MemberVo(memberVo.getId(), passwordEncoder.encode(memberVo.getPassword()), memberVo.getName(), Collections.emptyList());
    }

}
