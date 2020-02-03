package com.cglee079.podo.structure.member;

import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    public static final MemberDB MEMBER_DB = new MemberDB();

    public Member findById(String id){
        return MEMBER_DB.select(id);
    }

    public Member save(Member member){
        return MEMBER_DB.insert(member);
    }
}
