package com.cglee079.podo.structure.member;

import com.cglee079.podo.structure.core.vo.Role;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

public class MemberDB {

    private static long SEQ_INCREMENT = 0L;

    private final static Map<String, Member> MEMBERS;

    static {
        MEMBERS = new HashMap<>();
        final String id = "cglee079";
        final Member member = new Member(SEQ_INCREMENT++, id, "s1234", "이찬구");
        MEMBERS.put(id, member);
    }

    public Member select(String id) {
        return MEMBERS.get(id);
    }

    public Member insert(Member member) {
        final Member savedMember = new Member(SEQ_INCREMENT++, member.getId(), member.getPassword(), member.getName());
        savedMember.addRole(Role.MEMBER.toString());

        MEMBERS.put(member.getId(), savedMember);

        return savedMember;
    }
}
