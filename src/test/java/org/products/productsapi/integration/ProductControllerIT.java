package org.products.productsapi.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.products.productsapi.domain.Product;
import org.products.productsapi.repository.ProductRepository;
import org.products.productsapi.requests.ProductPostRequestBody;
import org.products.productsapi.util.ProductCreator;
import org.products.productsapi.util.ProductPostRequestBodyCreator;
import org.products.productsapi.wrapper.PageableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("listAll returns list of Products inside page object when successful")
    void listAll_ReturnsListOfProductsInsidePageObject_WhenSuccessful() {
        Product savedProduct = productRepository.save(ProductCreator.createProductToBeSaved());

        PageableResponse<Product> productPage = testRestTemplate.exchange("/products", HttpMethod.GET,
                null, new ParameterizedTypeReference<PageableResponse<Product>>() {
                }).getBody();

        Assertions.assertNotNull(productPage);
        Assertions.assertEquals(1, productPage.getTotalElements());
        Assertions.assertEquals(productPage.getContent().getFirst().getName(), savedProduct.getName());
    }

    @Test
    @DisplayName("getById returns Product when successful")
    void getById_ReturnsProduct_WhenSuccessful() {
        Product savedProduct = productRepository.save(ProductCreator.createProductToBeSaved());

        Product product = testRestTemplate.getForObject("/products/{id}", Product.class, savedProduct.getId());

        Assertions.assertNotNull(product);
        Assertions.assertEquals(savedProduct.getId(), product.getId());
    }

    @Test
    @DisplayName("listAll returns 400 when Product is not found")
    void getById_Returns400_WhenProductIsNotFound() {
        UUID randomUUID = UUID.randomUUID();

        ResponseEntity<Void> productResponseEntity = testRestTemplate.exchange("/products/{id}", HttpMethod.GET,
                null, Void.class, randomUUID);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, productResponseEntity.getStatusCode());
    }

    @Test
    @DisplayName("save returns Product when successful")
    void save_ReturnsProduct_WhenSuccessful() {
        ProductPostRequestBody productPostRequestBody = ProductPostRequestBodyCreator.createProductPostRequestBody();

        ResponseEntity<Product> productResponseEntity = testRestTemplate.postForEntity("/products",
                productPostRequestBody, Product.class);

        Assertions.assertNotNull(productResponseEntity);
        Assertions.assertNotNull(productResponseEntity.getBody());
        Assertions.assertNotNull(productResponseEntity.getBody().getId());
        Assertions.assertEquals(HttpStatus.CREATED.value(), productResponseEntity.getStatusCode().value());
        Assertions.assertEquals(productPostRequestBody.getName(), productResponseEntity.getBody().getName());
    }

    @Test
    @DisplayName("delete removes Product when successful")
    void delete_RemovesProduct_WhenSuccessful() {
        Product savedProduct = productRepository.save(ProductCreator.createProductToBeSaved());

        ResponseEntity<Void> productResponseEntity = testRestTemplate.exchange("/products/{id}", HttpMethod.DELETE,
                new HttpEntity<>(savedProduct), Void.class, savedProduct.getId());

        Assertions.assertEquals(HttpStatus.NO_CONTENT, productResponseEntity.getStatusCode());
    }

    @Test
    @DisplayName("delete returns 400 when when Product is not found")
    void delete_Returns400_WhenProductIsNotFound() {
        UUID randomUUID = UUID.randomUUID();

        ResponseEntity<Void> productResponseEntity = testRestTemplate.exchange("/products/{id}", HttpMethod.DELETE,
                null, Void.class, randomUUID);

        Assertions.assertNull(productResponseEntity.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, productResponseEntity.getStatusCode());
    }

    @Test
    @DisplayName("update updates Product when successful")
    void update_UpdatesProduct_WhenSuccessful() {
        Product savedProduct = productRepository.save(ProductCreator.createProductToBeSaved());

        Product newProduct = ProductCreator.createProductToBeSaved();

        ResponseEntity<Void> productResponseEntity = testRestTemplate.exchange("/products/{id}", HttpMethod.PUT,
                new HttpEntity<>(newProduct), Void.class, savedProduct.getId());

        Assertions.assertEquals(HttpStatus.NO_CONTENT, productResponseEntity.getStatusCode());
    }

    @Test
    @DisplayName("update updates Product when successful")
    void update_Returns400_WhenProductIsNotFound() {
        Product newProduct = ProductCreator.createProductToBeSaved();

        ResponseEntity<Void> productResponseEntity = testRestTemplate.exchange("/products/{id}", HttpMethod.PUT,
                new HttpEntity<>(newProduct), Void.class, UUID.randomUUID());

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, productResponseEntity.getStatusCode());
    }
}
