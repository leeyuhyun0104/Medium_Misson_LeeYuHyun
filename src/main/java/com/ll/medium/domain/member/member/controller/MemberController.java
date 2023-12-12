package com.ll.medium.domain.member.member.controller;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/join")
    public String showJoin(JoinForm joinForm) {
        return "domain/member/member/join";
    }

//@Getter
//@Setter
//public static class JoinForm {
//    @Size(min = 3, max = 25)
//    @NotEmpty(message = "사용자ID는 필수항목입니다.")
//    private String username;
//    @NotEmpty(message = "비밀번호는 필수항목입니다.")
//    private String password;
//    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
//    private String passwordConfirm;
//}

    @PostMapping("/join")
    public String join(@Valid JoinForm joinForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "domain/member/member/join";
        }

        if (!joinForm.getPassword().equals(joinForm.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "domain/member/member/join";
        }
        Member member = memberService.join(joinForm.getUsername(), joinForm.getPassword());

        long id = member.getId();

        return "redirect:/?msg=No %d member joined.".formatted(id);    }
}