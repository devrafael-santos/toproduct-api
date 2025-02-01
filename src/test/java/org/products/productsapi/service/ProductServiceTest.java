package org.products.productsapi.service;

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
import org.products.productsapi.exception.BadRequestException;
import org.products.productsapi.repository.ProductRepository;
import org.products.productsapi.util.ProductCreator;
import org.products.productsapi.util.ProductPostRequestBodyCreator;
import org.products.productsapi.util.ProductPutRequestBodyCreator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepositoryMock;

    @BeforeEach
    public void setUp() {
        PageImpl<Product> productPage = new PageImpl<>(List.of(ProductCreator.createValidProduct()));

        BDDMockito.when(productRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(productPage);

        BDDMockito.when(productRepositoryMock.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(ProductCreator.createValidProduct()));

        BDDMockito.when(productRepositoryMock.save(ArgumentMatchers.any(Product.class)))
                .thenReturn(ProductCreator.createValidProduct());


        BDDMockito.doNothing().when(productRepositoryMock).delete(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("listAll returns list of Product inside page object when successful")
    void listAll_ReturnsListOfProductsInsidePageObject_WhenSuccessful() {
        String expectedName = ProductCreator.createValidProduct().getName();

        Page<Product> productPage = productService.listAll(PageRequest.of(1, 1));

        Assertions.assertThat(productPage).isNotNull();
        Assertions.assertThat(productPage.toList()).isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(productPage.toList().getFirst().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns Product when successful")
    void findByIdOrThrowBadRequestException_ReturnsProduct_WhenSuccessful() {
        UUID expectedId = ProductCreator.createValidProduct().getId();

        Product product = productService.findByIdOrThrowBadRequestException(UUID.randomUUID());

        Assertions.assertThat(product).isNotNull();

        Assertions.assertThat(product.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws ReturnsBadRequestException when Product is not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenProductIsNotFound() {
        BDDMockito.when(productRepositoryMock.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> productService.findByIdOrThrowBadRequestException(UUID.randomUUID()))
                .isInstanceOf(BadRequestException.class);
    }


    @Test
    @DisplayName("save returns Product when successful")
    void save_ReturnsProduct_WhenSuccessful() {
        Product product = productService.save(ProductPostRequestBodyCreator.createProductPostRequestBody());

        Assertions.assertThat(product).isNotNull().isEqualTo(ProductCreator.createValidProduct());
    }

    @Test
    @DisplayName("update updates Product when successful")
    void update_UpdatesProduct_WhenSuccessful() {
        Assertions.assertThatCode(() -> productService
                        .update(UUID.randomUUID(), ProductPutRequestBodyCreator.createProductPutRequestBody()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("update throws ReturnsBadRequestException when Product is not found")
    void update_ThrowsBadRequestException_WhenProductIsNotFound() {
        BDDMockito.when(productRepositoryMock.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> productService
                        .update(UUID.randomUUID(), ProductPutRequestBodyCreator.createProductPutRequestBody()))
                .isInstanceOf(BadRequestException.class);
        ;
    }

    @Test
    @DisplayName("delete removes Product when successful")
    void delete_RemovesProduct_WhenSuccessful() {
        Assertions.assertThatCode(() -> productService.delete(UUID.randomUUID())
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete throws ReturnsBadRequestException when Product is not found")
    void delete_ThrowsBadRequestException_WhenProductIsNotFound() {
        BDDMockito.when(productRepositoryMock.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> productService.delete(UUID.randomUUID()))
                .isInstanceOf(BadRequestException.class);
    }
}