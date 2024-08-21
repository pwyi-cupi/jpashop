package jpashop.siljeon_jpashop.service;

import jpashop.siljeon_jpashop.domain.Member;
import jpashop.siljeon_jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    public void 회원가입() throws Exception{
        Member member=new Member();
        member.setName("kim");
        Long savedId = memberService.join(member);

        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        Member member1 = new Member();
        Member member2 = new Member();

        member1.setName("kim");
        member2.setName("kim");

        memberService.join(member1);
        try {
            memberService.join(member2);
        }catch(IllegalStateException e){
            return;
        }

        fail("예외가 발생해야 한다.");
    }


}