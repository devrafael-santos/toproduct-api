package org.products.productsapi.util;

import org.products.productsapi.domain.Product;
import org.products.productsapi.requests.ProductPutRequestBody;

public class ProductPutRequestBodyCreator {

    public static ProductPutRequestBody createProductPutRequestBody() {
        Product product = ProductCreator.createValidProduct();

        return ProductPutRequestBody.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .available(product.getAvailable())
                .build();
    }
}
