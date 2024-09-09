package org.prgrms.coffee_order_be.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.prgrms.coffee_order_be.model.entity.Order;

import java.util.List;

@Getter
public class CreateOderReq {
    @NotNull
    private List<OrderProductDto> orderItems;

    @NotNull
    private String email;

    @NotNull
    private String address;

    @NotNull
    private String postcode;

    public Order toOrder(){
        return Order.builder()
                .email(email)
                .address(address)
                .postcode(postcode)
                .build();
    }
}
