package org.products.productsapi.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductPutRequestBody {

    @NotEmpty(message = "The id of this product cannot be empty")
    private UUID id;

    @NotEmpty(message = "The name of this product cannot be empty")
    private String name;

    private String description;

    @NotEmpty(message = "The price of this product cannot be empty")
    private Double price;

    private String available;
}
