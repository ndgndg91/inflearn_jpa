package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    
    @Test
    @Rollback(false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("동길");
        
        //when
        Long savedId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(savedId));
        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(savedId));
     }
     
     @Test(expected = IllegalStateException.class)
     public void 중복_회원_가입() throws Exception {
         //given
         Member member1 = new Member();
         member1.setName("동길");

         Member member2 = new Member();
         member2.setName("동길");
         
         //when
         memberService.join(member1);
         memberService.join(member2);
         
         //then
         fail("예외가 발생합니다.");
      }

}