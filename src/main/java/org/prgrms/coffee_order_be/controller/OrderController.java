package org.prgrms.coffee_order_be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.coffee_order_be.exception.ErrorResponse;
import org.prgrms.coffee_order_be.model.dto.request.CreateOderReq;
import org.prgrms.coffee_order_be.model.dto.request.UpdateOrderReq;
import org.prgrms.coffee_order_be.model.dto.response.GetOrdersRes;
import org.prgrms.coffee_order_be.model.entity.Order;
import org.prgrms.coffee_order_be.model.entity.OrderItem;
import org.prgrms.coffee_order_be.model.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Order" , description = "Order 관련 API 모음")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성")
    @PostMapping
    public ErrorResponse<List<OrderItem>> createOrder(@RequestBody @Valid CreateOderReq req){
        List<OrderItem> resp = orderService.createOrder(req);

        return new ErrorResponse<>(resp);
    }

    @Operation(summary = "이메일로 주문 조회")
    @GetMapping
    public ErrorResponse<List<GetOrdersRes>> getOrder(@RequestParam("email") String email){
        List<GetOrdersRes> resp = orderService.getOrder(email);
        return new ErrorResponse<>(resp);
    }

    @Operation(summary = "주문 수정")
    @PutMapping("/{id}")
    public ErrorResponse<Order> updateOrder(@PathVariable("id") UUID uuid, @RequestBody @Valid UpdateOrderReq req){
        Order resp = orderService.updateOrder(uuid, req);
        return new ErrorResponse<>(resp);
    }

    @Operation(summary = "주문 삭제")
    @DeleteMapping("/{id}")
    public ErrorResponse<?> deleteOrder(@PathVariable("id") UUID uuid){
        String resp = orderService.deleteOrder(uuid);
        return new ErrorResponse<>(resp);
    }
}
