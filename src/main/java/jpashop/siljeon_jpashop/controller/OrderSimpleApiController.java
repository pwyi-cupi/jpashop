package jpashop.siljeon_jpashop.controller;

import jpashop.siljeon_jpashop.domain.*;
import jpashop.siljeon_jpashop.repository.OrderRepository;
import jpashop.siljeon_jpashop.repository.orderSimpleQuery.OrderSimpleQueryDto;
import jpashop.siljeon_jpashop.repository.orderSimpleQuery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    //엔티티를 DTO로 변환
    @GetMapping("api/simple-orders/v2")
    public List<SimpleOrderDto> ordersV2(){
        List<Order> orders=orderRepository.findAll(new OrderSearch());
        List<SimpleOrderDto> results = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return results;
    }


    //엔티티를 DTO로 변환 + 페치 조인으로 성능 최적화
    @GetMapping("/api/simple-orders")
    public List<SimpleOrderDto> orders(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    //JPA에서 DTO로 바로 조회
    @GetMapping("/api/simple-orders/v4")
    public List<OrderSimpleQueryDto> ordersV4(){
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto (Order order){
            orderId=order.getId();
            name=order.getMember().getName();
            orderDate=order.getOrderDate();
            orderStatus=order.getStatus();
            address=order.getDelivery().getAddress();
        }
    }
}
