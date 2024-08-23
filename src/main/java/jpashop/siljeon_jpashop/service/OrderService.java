package jpashop.siljeon_jpashop.service;

import jpashop.siljeon_jpashop.domain.Delivery;
import jpashop.siljeon_jpashop.domain.Item.Item;
import jpashop.siljeon_jpashop.domain.Member;
import jpashop.siljeon_jpashop.domain.Order;
import jpashop.siljeon_jpashop.domain.OrderItem;
import jpashop.siljeon_jpashop.repository.ItemRepository;
import jpashop.siljeon_jpashop.repository.MemberRepository;
import jpashop.siljeon_jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문 생성
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery=new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order= Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    //주문 취소
    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }
    
    //주문 검색
}
