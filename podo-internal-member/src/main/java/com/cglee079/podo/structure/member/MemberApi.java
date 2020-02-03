package com.cglee079.podo.structure.member;

import com.cglee079.podo.structure.core.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberApi {

    private final MemberRepository memberRepository;

    @GetMapping("/api/members/{memberId}")
    public MemberVo getMember(@PathVariable("memberId") String memberId) {
        final Member member = memberRepository.findById(memberId);
        return new MemberVo(member.getId(), member.getPassword(), member.getName(), member.getRoles());
    }

    @PostMapping("/api/members")
    public String newMember(@RequestBody MemberVo memberVo) {
        final Member member = new Member(memberVo);
        final Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }

}
