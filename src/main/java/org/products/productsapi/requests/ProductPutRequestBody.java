package org.products.productsapi.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ProductPutRequestBody {

    @NotNull(message = "The id of this product cannot be empty")
    private UUID id;

    @NotEmpty(message = "The name of this product cannot be empty")
    private String name;

    private String description;

    @NotNull(message = "The price of this product cannot be empty")
    private Double price;

    private Boolean available;
}
