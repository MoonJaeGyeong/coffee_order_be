package org.prgrms.coffee_order_be.model.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.coffee_order_be.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @DisplayName("상품 저장 테스트")
    @Test
    public void testSave(){
        // given

        product = Product.builder()
                .price(5000)
                .description("test_de")
                .category("test_cate")
                .productName("coffee")
                .build();

        // when
        Product savedProduct = productRepository.save(product);


        // then
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getPrice()).isEqualTo(5000);
        assertThat(savedProduct.getProductName()).isEqualTo("coffee");
        assertThat(savedProduct.getCategory()).isEqualTo("test_cate");
        assertThat(savedProduct.getDescription()).isEqualTo("test_de");

    }

}