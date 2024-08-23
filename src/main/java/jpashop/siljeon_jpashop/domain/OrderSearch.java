package jpashop.siljeon_jpashop.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    public String memberName; //검색할 회원 이름
    public OrderStatus orderStatus; //주문 상태[ORDER, CANCEL]
}
