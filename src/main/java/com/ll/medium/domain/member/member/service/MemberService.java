package com.ll.medium.domain.member.member.service;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public Member join(String username, String password) {
        Member member = new Member(username, password);
        member.setPassword(passwordEncoder.encode(password));
        memberRepository.save(member);
        return member;
    }
}
