package org.products.productsapi.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductPostRequestBody {

    @NotEmpty(message = "The name of this product cannot be empty")
    private String name;

    private String description;

    @NotNull(message = "The price of this product cannot be empty")
    private Double price;

    private Boolean available;
}
