package org.products.productsapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.products.productsapi.domain.Product;
import org.products.productsapi.requests.ProductPostRequestBody;
import org.products.productsapi.requests.ProductPutRequestBody;
import org.products.productsapi.service.ProductService;
import org.products.productsapi.util.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("products")
@Log4j2
@RequiredArgsConstructor
public class ProductController {

    private final DateUtil dateUtil;
    private final ProductService productService;

    @GetMapping
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public ResponseEntity<Page<Product>> listAll(Pageable pageable) {
        log.info(dateUtil.formatLocalDateToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(productService.listAll(pageable));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Product> getById(@PathVariable UUID id) {
        return new ResponseEntity<>(productService.findByIdOrThrowBadRequestException(id), HttpStatus.OK);
    }

    @PostMapping
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public ResponseEntity<Product> save(@RequestBody ProductPostRequestBody productPostRequestBody) {
        return new ResponseEntity<>(productService.save(productPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProductPutRequestBody productPutRequestBody) {
        productService.update(productPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
