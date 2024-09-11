package org.prgrms.coffee_order_be.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.prgrms.coffee_order_be.model.dto.ProductDto;
import org.prgrms.coffee_order_be.model.dto.request.UpdateProductReq;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id", columnDefinition = "BINARY(16)")
    private UUID id;

    private String productName;

    private String category;

    private long price;

    private String description;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @Builder
    public Product(String productName, String category, long price, String description) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public ProductDto toDto(){
        return ProductDto.builder()
                .productName(productName)
                .category(category)
                .description(description)
                .price(price)
                .build();
    }

    public void update(UpdateProductReq req){
        this.price = req.getPrice();
        this.description = req.getDescription();
    }
}
