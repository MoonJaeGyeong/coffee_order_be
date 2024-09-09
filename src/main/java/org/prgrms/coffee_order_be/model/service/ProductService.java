package org.prgrms.coffee_order_be.model.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.coffee_order_be.exception.ErrorCode;
import org.prgrms.coffee_order_be.exception.ErrorException;
import org.prgrms.coffee_order_be.model.dto.ProductDto;
import org.prgrms.coffee_order_be.model.dto.request.CreateProductReq;
import org.prgrms.coffee_order_be.model.dto.request.UpdateProductReq;
import org.prgrms.coffee_order_be.model.dto.response.GetProductsRes;
import org.prgrms.coffee_order_be.model.entity.Product;
import org.prgrms.coffee_order_be.model.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto createProduct(CreateProductReq req){
        Product product = req.toProduct();

        productRepository.save(product);

        return product.toDto();
    }

    public List<GetProductsRes> getProducts(){
        List<Product> products = productRepository.findAll();

        return products.stream().map(GetProductsRes::new).toList();
    }

    public ProductDto getProduct(UUID uuid){
        Product product = productRepository.findById(uuid)
                .orElseThrow( () -> new ErrorException(ErrorCode.NOT_EXIST_PRODUCT));

        return product.toDto();
    }

    public String deleteProduct(UUID uuid){
        productRepository.deleteById(uuid);
        return "Delete Success";
    }

    public ProductDto updateProduct(UUID uuid, UpdateProductReq req){
        Product product = productRepository.findById(uuid)
                .orElseThrow( () ->  new ErrorException(ErrorCode.NOT_EXIST_PRODUCT));

        product = product.update(req);
        productRepository.save(product);

        return product.toDto();
    }
}
