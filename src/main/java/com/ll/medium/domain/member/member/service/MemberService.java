package com.ll.medium.domain.member.member.service;

import com.ll.medium.DataNotFoundException;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member join(String username, String password, boolean isPaid) {
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(passwordEncoder.encode(password));
        member.setPaid(isPaid);
        this.memberRepository.save(member);
        return member;
    }

    @Transactional
    public void upgradeToPaidMember(String username){
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        Member member = optionalMember.orElse(null);
        if(member != null){
            member.setPaid(true);
            memberRepository.save(member);
        }
        else{
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
    }

    public boolean isPaidMember(String username){
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        return optionalMember.map(Member::isPaid).orElse(false);
    }

    public Member getMember(String username) {
        Optional<Member> member = this.memberRepository.findByUsername(username);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("member not found");
        }
    }
}
