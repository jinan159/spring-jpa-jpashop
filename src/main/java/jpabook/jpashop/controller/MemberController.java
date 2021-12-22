package jpabook.jpashop.controller;

import jpabook.jpashop.controller.dto.MemberForm;
import jpabook.jpashop.controller.dto.MemberResponseDto;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());

        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Member newMember = form.toEntity();

        memberService.join(newMember);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {

        List<Member> members = memberService.findMembers();
        List<MemberResponseDto> memberResponseList = new ArrayList<>();

        for (Member member : members) {
            memberResponseList.add(modelMapper.map(member, MemberResponseDto.class));
        }

        model.addAttribute("members", memberResponseList);

        return "members/memberList";
    }
}