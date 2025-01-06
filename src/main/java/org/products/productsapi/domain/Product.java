package org.products.productsapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @NotBlank(message = "The name of the product cannot be blank")
    private String name;

    private String description;

    @Column(nullable = false)
    @NotNull(message = "The price of the product cannot be null")
    private Double price;

    @Column(nullable = false)
    private Boolean available = true; // Define um valor padrão

}
