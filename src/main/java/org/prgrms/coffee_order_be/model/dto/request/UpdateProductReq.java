package org.prgrms.coffee_order_be.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateProductReq {
    @NotNull
    private long price;

    @NotNull
    private String description;
}
