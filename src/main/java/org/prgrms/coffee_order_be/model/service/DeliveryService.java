package org.prgrms.coffee_order_be.model.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.coffee_order_be.model.entity.Order;
import org.prgrms.coffee_order_be.model.entity.OrderStatus;
import org.prgrms.coffee_order_be.model.repository.OrderRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final OrderRepository orderRepository;

    @Scheduled(cron = "0 0 14 * * *", zone = "Asia/Seoul")
    public void delivery(){
        List<Order> orderList = orderRepository.findAllByOrderStatus(OrderStatus.ORDER_COMPLETED);
        for (Order order : orderList) {
            order.setOrderStatus(OrderStatus.IN_DELIVERY);
        }
    }
}
