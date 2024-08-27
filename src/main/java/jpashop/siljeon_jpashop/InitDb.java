package jpashop.siljeon_jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpashop.siljeon_jpashop.domain.*;
import jpashop.siljeon_jpashop.domain.Item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void initDb(){
        initService.dbInit1();
        initService.dbInit2();
    }


    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService{

        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("userA", "서울", "동작구", "123-123");
            em.persist(member);

            Book book1 = createBook("JPA1 BOOK", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 20000, 100);
            em.persist(book2);

            OrderItem orderItem1=OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2=OrderItem.createOrderItem(book2, 20000, 2);
            Order order = Order.createOrder(member, createDelivery(member), orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = createMember("userB", "부산", "서면", "123-123");
            em.persist(member);

            Book book1 = createBook("SPRING1 BOOK", 20000, 100);
            em.persist(book1);

            Book book2 = createBook("SPRING2 BOOK", 40000, 100);
            em.persist(book2);

            OrderItem orderItem1=OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2=OrderItem.createOrderItem(book2, 40000, 4);
            Order order = Order.createOrder(member, createDelivery(member), orderItem1, orderItem2);
            em.persist(order);
        }

        private static Delivery createDelivery(Member member) {
            Delivery delivery=new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book1=new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member=new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }
    }
}