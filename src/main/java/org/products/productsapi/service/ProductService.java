package org.products.productsapi.service;

import lombok.RequiredArgsConstructor;
import org.products.productsapi.domain.Product;
import org.products.productsapi.exception.BadRequestException;
import org.products.productsapi.mapper.ProductMapper;
import org.products.productsapi.repository.ProductRepository;
import org.products.productsapi.requests.ProductPostRequestBody;
import org.products.productsapi.requests.ProductPutRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> listAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product findByIdOrThrowBadRequestException(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Product not found"));
    }

    @Transactional
    public Product save(ProductPostRequestBody productPostRequestBody) {
        return productRepository.save(ProductMapper.INSTANCE.toProduct(productPostRequestBody));
    }

    public void delete(UUID id) {
        productRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    @Transactional
    public void update(UUID id, ProductPutRequestBody productPutRequestBody) {
        Product savedProduct = findByIdOrThrowBadRequestException(id);

        Product product = ProductMapper.INSTANCE.toProduct(productPutRequestBody);
        product.setId(savedProduct.getId());

        productRepository.save(product);
    }
}
