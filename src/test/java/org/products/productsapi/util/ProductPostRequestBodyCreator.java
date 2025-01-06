package org.products.productsapi.util;

import org.products.productsapi.domain.Product;
import org.products.productsapi.requests.ProductPostRequestBody;

public class ProductPostRequestBodyCreator {

    public static ProductPostRequestBody createProductPostRequestBody() {
        Product product = ProductCreator.createProductToBeSaved();

        return ProductPostRequestBody.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .available(product.getAvailable())
                .build();
    }
}
