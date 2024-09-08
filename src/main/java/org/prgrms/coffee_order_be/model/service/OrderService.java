package org.prgrms.coffee_order_be.model.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.coffee_order_be.model.dto.OrderItemDto;
import org.prgrms.coffee_order_be.model.dto.request.OrderProductDto;
import org.prgrms.coffee_order_be.model.dto.request.CreateOderReq;
import org.prgrms.coffee_order_be.model.dto.response.GetOrdersRes;
import org.prgrms.coffee_order_be.model.entity.Order;
import org.prgrms.coffee_order_be.model.entity.OrderItem;
import org.prgrms.coffee_order_be.model.entity.Product;
import org.prgrms.coffee_order_be.model.repository.OrderItemRepository;
import org.prgrms.coffee_order_be.model.repository.OrderRepository;
import org.prgrms.coffee_order_be.model.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<GetOrdersRes> getOrders(String email){
        List<Order> orders = orderRepository.findAllByEmail(email);
        List<GetOrdersRes> getOrdersResList = new ArrayList<>();

        for(Order order : orders){
            List<OrderItem> orderItems = orderItemRepository.findAllByOrder(order);
            List<OrderItemDto> orderItemDtos = orderItems.stream().map(OrderItem::toDto).toList();
            GetOrdersRes getOrdersRes = new GetOrdersRes(orderItemDtos);

            if(!orderItemDtos.isEmpty())
                getOrdersResList.add(getOrdersRes);
        }

        return getOrdersResList;
    }
}
