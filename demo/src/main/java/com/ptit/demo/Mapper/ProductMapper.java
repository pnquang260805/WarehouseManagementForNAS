package com.ptit.demo.Mapper;

import com.ptit.demo.DTO.ProductRequest;
import com.ptit.demo.Model.Products;
import com.ptit.demo.DTO.ProductsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "providerId", expression = "java(product.getProviders().getId())")
    @Mapping(target = "stockId", expression = "java(product.getStocks().stream().map(p -> p.getId()).toList())")
    ProductsResponse toResponse(Products product);
    List<ProductsResponse> toResponse(List<Products> product);

    Products toProduct(ProductRequest request);
}
