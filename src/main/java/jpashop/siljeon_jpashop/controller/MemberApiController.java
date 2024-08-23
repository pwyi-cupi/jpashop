package jpashop.siljeon_jpashop.controller;

import jpashop.siljeon_jpashop.domain.Member;
import jpashop.siljeon_jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    //회원 등록
    @PostMapping("/api/members")
    public CreateMemberResponse saveMember(@RequestBody @Validated CreateMemberRequest request){
        Member member=new Member();
        member.setName(request.name);
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    //회원 수정
    @PutMapping("/api/members/{id}")
    public UpdateMemberResponse updateMember(@PathVariable("id")Long id, @RequestBody @Validated UpdateMemberRequest request){
        memberService.update(id, request.name);
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    //회원 조회
    @GetMapping("api/members")
    public Result membersAll(){
        List<Member> findMembers = memberService.findMembers();
        /*
        초보자 코드
        List<MemberDto> list=new ArrayList<>();
        for(Member member : collect){
            MemberDto m=new MemberDto(member.getName());
            list.add(m);
        }*/
        List<MemberDto> collect=findMembers.stream()
                .map(m->new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{ //JSON 데이터를 object 형식으로 만들기 위함
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }


    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        Long id;
        String name;
    }

    @Data
    static class UpdateMemberRequest{
        String name;
    }

    @Data
    static class CreateMemberResponse {
        Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class CreateMemberRequest{
        String name;
    }
}
