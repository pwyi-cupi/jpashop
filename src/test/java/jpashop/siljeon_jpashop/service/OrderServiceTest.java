package jpashop.siljeon_jpashop.service;

import jakarta.persistence.EntityManager;
import jpashop.siljeon_jpashop.domain.Address;
import jpashop.siljeon_jpashop.domain.Item.Book;
import jpashop.siljeon_jpashop.domain.Item.Item;
import jpashop.siljeon_jpashop.domain.Member;
import jpashop.siljeon_jpashop.domain.Order;
import jpashop.siljeon_jpashop.domain.OrderStatus;
import jpashop.siljeon_jpashop.exception.NotEnoughStockException;
import jpashop.siljeon_jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired EntityManager em; //MemberRepository와 ItemRepository를 불러오지 않고 바로 영속성 컨텍스트로 보냄

    @Test
    public void 상품주문() throws Exception{
        Member member = createMember("회원1");
        Item item = createItem("책", 10, 10000);

        int orderCount=2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "주문 상태가 ORDER이어야 한다.");
        assertEquals(1, getOrder.getOrderItems().size());
        assertEquals(20000, getOrder.getTotalPrice(), "주문 총 금액이 같아야 한다");
        assertEquals(8, item.getStockQuantity(), "남은 수량의 개수가 같아야 한다.");

    }

    @Test
    public void 상품주문_재고수량초과() throws Exception{
        Member member = createMember("kim");
        Item book = createItem("책", 10, 10000);

        int orderCount=11;
        try {
            orderService.order(member.getId(), book.getId(), orderCount);
        }catch(NotEnoughStockException e){
            return;
        }
        fail("예외가 발생해야 한다.");
    }

    @Test
    public void 주문취소() throws Exception{
        Member member = createMember("kim");
        Item book = createItem("책", 10, 10000);

        int orderCount=2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        orderService.cancelOrder(orderId);
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 취소 상태가 같아야 한다.");
        assertEquals(10, book.getStockQuantity(), "증가된 재고 수량이 같아야 한다.");

    }


    private Item createItem(String name, int stockQuantity, int price) {
        Item item=new Book();
        item.setName(name);
        item.setStockQuantity(stockQuantity);
        item.setPrice(price);
        em.persist(item);
        return  item;
    }

    private Member createMember(String name) {
        Member member=new Member();
        member.setName(name);
        member.setAddress(new Address("서울", "동작구", "123-123"));
        em.persist(member);
        return member;
    }
}