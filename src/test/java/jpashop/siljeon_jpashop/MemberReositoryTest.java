package jpashop.siljeon_jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MemberReositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    
    @Test
    @Transactional
    @Rollback(false)
    public void save() throws Exception{
        Member member=new Member();
        member.setUsername("memberA");

        Member memberA = memberRepository.save(member);
        Member findMember = memberRepository.findById(memberA.getId()).orElse(null);

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);
    }
}
