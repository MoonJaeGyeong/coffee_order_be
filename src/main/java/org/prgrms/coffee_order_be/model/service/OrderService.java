package org.prgrms.coffee_order_be.model.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.coffee_order_be.model.dto.request.UpdateOrderReq;
import org.prgrms.coffee_order_be.model.dto.OrderItemDto;
import org.prgrms.coffee_order_be.model.dto.request.OrderProductDto;
import org.prgrms.coffee_order_be.model.dto.request.CreateOderReq;
import org.prgrms.coffee_order_be.model.dto.response.GetOrdersRes;
import org.prgrms.coffee_order_be.model.entity.Order;
import org.prgrms.coffee_order_be.model.entity.OrderItem;
import org.prgrms.coffee_order_be.model.entity.OrderStatus;
import org.prgrms.coffee_order_be.model.entity.Product;
import org.prgrms.coffee_order_be.model.repository.OrderItemRepository;
import org.prgrms.coffee_order_be.model.repository.OrderRepository;
import org.prgrms.coffee_order_be.model.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public List<OrderItem> createOrder(CreateOderReq req){
        Order order = req.toOrder();
        List<OrderItem> orderItems = new ArrayList<>();

        for(OrderProductDto orderItem : req.getOrderItems()){
            Product product = productRepository.findById(orderItem.getProductsUUID())
                    .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));

            orderItems.add(OrderItem.builder()
                    .product(product)
                    .order(order)
                    .quantity(orderItem.getQuantity())
                    .category(product.getCategory())
                    .price(product.getPrice() * orderItem.getQuantity())
                    .build());
        }

        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        return orderItems;
    }

    public List<GetOrdersRes> getOrder(String email){
        List<Order> orders = orderRepository.findAllByEmail(email);
        if(orders.isEmpty())
            throw new RuntimeException("주문 내역이 없습니다.");

        List<GetOrdersRes> getOrdersResList = new ArrayList<>();

        for(Order order : orders){
            List<OrderItem> orderItems = orderItemRepository.findAllByOrder(order);
            List<OrderItemDto> orderRes = orderItems.stream().map(OrderItem::toDto).toList();
            GetOrdersRes getOrdersRes = new GetOrdersRes(order.getId(), orderRes, order.getOrderStatus(), order.getAddress(), order.getPostcode());

            if(!orderRes.isEmpty())
                getOrdersResList.add(getOrdersRes);
        }

        return getOrdersResList;
    }


    public Order updateOrder(UUID uuid, UpdateOrderReq req){
        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 주문 입니다."));

        if(order.getOrderStatus() != OrderStatus.ORDER_COMPLETED)
            throw new RuntimeException("배달이 진행 중이므로 상담원에게 전화 연락 부탁드립니다.");

        order.update(req.getAddress(), req.getPostcode());
        orderRepository.save(order);

        return order;
    }

    public String deleteOrder(UUID uuid){
        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 주문 입니다."));

        if(order.getOrderStatus() != OrderStatus.ORDER_COMPLETED)
            throw new RuntimeException("배달이 진행 중이므로 삭제가 불가능합니다.");

        orderRepository.delete(order);

        return "Success delete";
    }

}
