package org.products.productsapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.products.productsapi.domain.Product;
import org.products.productsapi.requests.ProductPostRequestBody;
import org.products.productsapi.requests.ProductPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    public abstract Product toProduct(ProductPostRequestBody productPostRequestBody);

    public abstract Product toProduct(ProductPutRequestBody productPutRequestBody);
}
