package org.products.productsapi.repository;

import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.products.productsapi.domain.Product;
import org.products.productsapi.util.ProductCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@DisplayName("Unit Tests for Product repository")
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Save persists Product when successful")
    void save_PersistsProduct_WhenSuccessful() {
        Product productToBeSaved = ProductCreator.createProductToBeSaved();
        Product productSaved = this.productRepository.save(productToBeSaved);

        Assertions.assertThat(productSaved).isNotNull();
        Assertions.assertThat(productSaved.getId()).isNotNull();
        Assertions.assertThat(productSaved.getName()).isEqualTo(productToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates Product when successful")
    void save_UpdatesProduct_WhenSuccessful() {
        Product productToBeSaved = ProductCreator.createProductToBeSaved();
        Product productSaved = this.productRepository.save(productToBeSaved);

        productSaved.setName("Smartphone");

        Product productUpdated = this.productRepository.save(productSaved);

        Assertions.assertThat(productUpdated).isNotNull();
        Assertions.assertThat(productUpdated.getId()).isNotNull();
        Assertions.assertThat(productUpdated.getName()).isEqualTo(productSaved.getName());
    }


    @Test
    @DisplayName("Delete removes Product when successful")
    void delete_RemovesProduct_WhenSuccessful() {
        Product productToBeSaved = ProductCreator.createProductToBeSaved();
        Product productSaved = this.productRepository.save(productToBeSaved);

        this.productRepository.delete(productSaved);

        Optional<Product> product = this.productRepository.findById(productSaved.getId());

        Assertions.assertThat(product).isEmpty();
    }

    @Test
    @DisplayName("FindById returns a product when successful")
    void findById_ReturnsAProduct_WhenSuccessful() {
        Product productToBeSaved = ProductCreator.createProductToBeSaved();
        Product productSaved = this.productRepository.save(productToBeSaved);

        UUID id = productSaved.getId();

        Optional<Product> product = this.productRepository.findById(id);

        Assertions.assertThat(product)
                .isNotEmpty()
                .contains(productSaved);
    }

    @Test
    @DisplayName("FindById returns empty when Product is not found")
    void findById_ReturnsEmpty_WhenProductIsNotFound() {
        Optional<Product> products = this.productRepository.findById(UUID.randomUUID());

        Assertions.assertThat(products).isEmpty();
    }

    @Test
    @DisplayName("Save throws an MethodArgumentNotValidException when the name is empty")
    void save_ThrowsMethodArgumentNotValidException_WhenNameIsEmpty() {
        Product product = ProductCreator.createProductWithEmptyNameToBeSaved();

        Assertions.assertThatThrownBy(() -> {
            productRepository.save(product);
            productRepository.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Save throws an DataIntegrityViolationException when the price is null")
    void save_ThrowsDataIntegrityViolationException_WhenPriceIsNull() {
        Product product = ProductCreator.createProductWithNullPriceToBeSaved();

        Assertions.assertThatThrownBy(() -> {
            productRepository.save(product);
            productRepository.flush();
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

}