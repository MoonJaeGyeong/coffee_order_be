package org.prgrms.coffee_order_be.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.prgrms.coffee_order_be.model.entity.Product;

@Getter
public class CreateProductReq {

    @NotNull
    private String productName;

    @NotNull
    private String category;

    @NotNull
    private long price;

    @NotNull
    private String description;

    public Product toProduct(){
        return Product.builder()
                .productName(this.productName)
                .category(this.category)
                .price(this.price)
                .description(this.description)
                .build();
    }
}
