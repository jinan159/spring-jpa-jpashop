package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    @DisplayName("회원가입이 성공해야한다")
    public void join() throws Exception {
        // given
        Member member = new Member();
        member.setName("jinwan");

        // when
        Long savedId = memberService.join(member);
        Member findMember = memberRepository.findOne(savedId);

        // then
        assertThat(member).isEqualTo(findMember);
    }

    @Test
    @DisplayName("중복된 이름의 회원가입 요청은 실패해야한다")
    public void duplicated_join() throws Exception {
        // given
        Member member = new Member();
        member.setName("jinan");

        Member duplicatedMember = new Member();
        duplicatedMember.setName(member.getName());

        // when
        memberService.join(member);

        // then
        assertThrows(IllegalStateException.class, () -> memberService.join(duplicatedMember));
    }
}