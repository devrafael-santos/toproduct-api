package org.products.productsapi.util;

import org.products.productsapi.domain.Product;

import java.util.UUID;

public class ProductCreator {

    private static final UUID id = UUID.randomUUID();

    public static Product createProductToBeSaved() {
        return Product.builder()
                .name("Mousepad")
                .description("A mouse pad")
                .price(1291.99)
                .available(true)
                .build();
    }

    public static Product createProductWithEmptyNameToBeSaved() {
        return Product.builder()
                .name("")
                .description("A mouse pad")
                .price(1291.99)
                .available(true)
                .build();
    }

    public static Product createProductWithNullPriceToBeSaved() {
        return Product.builder()
                .name("Mousepad")
                .description("A mouse pad")
                .price(1291.99)
                .available(null)
                .build();
    }

    public static Product createValidProduct() {
        return Product.builder()
                .id(id)
                .name("Mousepad")
                .description("A mouse pad")
                .price(1291.99)
                .available(true)
                .build();
    }

    public static Product createValidUpdatedProduct() {
        return Product.builder()
                .id(id)
                .name("Mousepad")
                .description("A mouse pad")
                .price(1291.99)
                .available(true)
                .build();
    }
}
