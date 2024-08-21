package jpashop.siljeon_jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded //@Embedded와 @Enumerated 헷갈리지 말기
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders=new ArrayList<>();
}
