package org.prgrms.coffee_order_be.model.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.coffee_order_be.exception.ErrorCode;
import org.prgrms.coffee_order_be.exception.ErrorException;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public List<OrderItem> createOrder(CreateOderReq req){
        Order order = req.toOrder();
        orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();

        for(OrderProductDto orderItem : req.getOrderItems()){
            Product product = productRepository.findById(orderItem.getProductsUUID())
                    .orElseThrow(() -> new ErrorException(ErrorCode.NOT_EXIST_PRODUCT));

            orderItems.add(OrderItem.builder()
                    .product(product)
                    .order(order)
                    .quantity(orderItem.getQuantity())
                    .category(product.getCategory())
                    .price(product.getPrice() * orderItem.getQuantity())
                    .build());
        }

        orderItemRepository.saveAll(orderItems);

        return orderItems;
    }

    public List<GetOrdersRes> getOrder(String email){
        List<Order> orders = orderRepository.findAllByEmailOrderByCreatedAtDesc(email);
        if(orders.isEmpty())
            throw new ErrorException(ErrorCode.NOT_EXIST_ORDER);

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

    @Transactional
    public Order updateOrder(UUID uuid, UpdateOrderReq req){
        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_EXIST_ORDER));

        if(order.getOrderStatus() != OrderStatus.ORDER_COMPLETED)
            throw new ErrorException(ErrorCode.IN_PROGRESS_DELIVERY);

        order.update(req.getAddress(), req.getPostcode());

        return order;
    }

    @Transactional
    public String deleteOrder(UUID uuid){
        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_EXIST_ORDER));

        if(order.getOrderStatus() != OrderStatus.ORDER_COMPLETED)
            throw new ErrorException(ErrorCode.IN_PROGRESS_DELIVERY);

        orderRepository.delete(order);

        return "Success delete";
    }

}
