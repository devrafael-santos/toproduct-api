package org.products.productsapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.products.productsapi.domain.Product;
import org.products.productsapi.requests.ProductPostRequestBody;
import org.products.productsapi.requests.ProductPutRequestBody;
import org.products.productsapi.service.ProductService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("products")
@Log4j2
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @CrossOrigin(origins = {"http://127.0.0.1:5500", "https://toproduct-app.pages.dev/"})
    @PageableAsQueryParam
    @Operation(summary = "List all Products paginated",
            description = "Returns a page of Products with default size 20. You can change it.",
            tags = {"product"})
    public ResponseEntity<Page<Product>> listAll(@Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(productService.listAll(pageable));
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get one product by ID", tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "When Product does not exists in the database")
    })
    public ResponseEntity<Product> getById(@PathVariable UUID id) {
        return new ResponseEntity<>(productService.findByIdOrThrowBadRequestException(id), HttpStatus.OK);
    }

    @PostMapping
    @CrossOrigin(origins = {"http://127.0.0.1:5500", "https://toproduct-app.pages.dev/"})
    @Operation(summary = "Save a Product",
            description = "Returns a new Product.",
            tags = {"product"})
    public ResponseEntity<Product> save(@RequestBody @Validated ProductPostRequestBody productPostRequestBody) {
        return new ResponseEntity<>(productService.save(productPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete one Product by ID", tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "When Product does not exists in the database")
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    @Operation(summary = "Update one Product by body", tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "When Product does not exists in the database")
    })
    public ResponseEntity<Void> update(@RequestBody @Validated ProductPutRequestBody productPutRequestBody) {
        productService.update(productPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
