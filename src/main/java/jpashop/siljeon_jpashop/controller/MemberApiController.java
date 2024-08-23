package jpashop.siljeon_jpashop.controller;

import jpashop.siljeon_jpashop.domain.Member;
import jpashop.siljeon_jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/api/members")
    public CreateMemberResponse saveMember(@RequestBody @Validated CreateMemberRequest request){
        Member member=new Member();
        member.setName(request.name);
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    class CreateMemberResponse {
        Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    class CreateMemberRequest{
        String name;
    }
}
