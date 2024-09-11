package org.prgrms.coffee_order_be.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.prgrms.coffee_order_be.model.dto.OrderItemDto;

import java.time.LocalDateTime;

@Entity(name = "order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private long id;

    private String category;

    private long price;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public OrderItem(String category, long price, int quantity, Order order, Product product) {
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
        this.product = product;
    }

    public OrderItemDto toDto(){
        return OrderItemDto.builder()
                .productName(product.getProductName())
                .quantity(quantity)
                .category(category)
                .price(price)
                .build();
    }
}
