package org.products.productsapi.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.products.productsapi.domain.Product;
import org.products.productsapi.requests.ProductPostRequestBody;
import org.products.productsapi.requests.ProductPutRequestBody;
import org.products.productsapi.service.ProductService;
import org.products.productsapi.util.ProductCreator;
import org.products.productsapi.util.ProductPostRequestBodyCreator;
import org.products.productsapi.util.ProductPutRequestBodyCreator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productServiceMock;

    @BeforeEach
    public void setUp() {
        PageImpl<Product> productPage = new PageImpl<>(List.of(ProductCreator.createValidProduct()));

        BDDMockito.when(productServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(productPage);

        BDDMockito.when(productServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.any()))
                .thenReturn(ProductCreator.createValidProduct());

        BDDMockito.when(productServiceMock.save(ArgumentMatchers.any(ProductPostRequestBody.class)))
                .thenReturn(ProductCreator.createValidProduct());

        BDDMockito.doNothing().when(productServiceMock).update(ArgumentMatchers.any(ProductPutRequestBody.class));

        BDDMockito.doNothing().when(productServiceMock).delete(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("listAll returns list of Product inside page object when successful")
    void listAll_ReturnsListOfProductsInsidePageObject_WhenSuccessful() {
        String expectedName = ProductCreator.createValidProduct().getName();

        Page<Product> productPage = productController.listAll(null).getBody();

        Assertions.assertThat(productPage).isNotNull();
        Assertions.assertThat(productPage.toList()).isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(productPage.toList().getFirst().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns Product when successful")
    void findById_ReturnsProduct_WhenSuccessful() {
        UUID expectedId = ProductCreator.createValidProduct().getId();

        Product product = productController.getById(UUID.randomUUID()).getBody();

        Assertions.assertThat(product).isNotNull();

        Assertions.assertThat(product.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findById returns Product when successful")
    void findById_ReturnsBadRequestException_WhenSuccessful() {
        UUID expectedId = ProductCreator.createValidProduct().getId();

        Product product = productController.getById(UUID.randomUUID()).getBody();

        Assertions.assertThat(product).isNotNull();

        Assertions.assertThat(product.getId()).isNotNull().isEqualTo(expectedId);
    }


    @Test
    @DisplayName("save returns Product when successful")
    void save_ReturnsProduct_WhenSuccessful() {
        Product product = productController.save(ProductPostRequestBodyCreator.createProductPostRequestBody()).getBody();

        Assertions.assertThat(product).isNotNull().isEqualTo(ProductCreator.createValidProduct());
    }

    @Test
    @DisplayName("update updates Product when successful")
    void update_UpdatesProduct_WhenSuccessful() {
        ResponseEntity<Void> entity =  productController.update(ProductPutRequestBodyCreator.createProductPutRequestBody());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes Product when successful")
    void delete_RemovesProduct_WhenSuccessful() {
        ResponseEntity<Void> entity =  productController.delete(UUID.randomUUID());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}