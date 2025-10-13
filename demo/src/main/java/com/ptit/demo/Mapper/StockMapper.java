package com.ptit.demo.Mapper;

import com.ptit.demo.DTO.StockResponse;
import com.ptit.demo.Model.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMapper {
    @Mapping(target = "productId", expression = "java(stock.getProduct().getId())")
    @Mapping(target = "warehouseId", expression = "java(stock.getWarehouse().getId())")
    StockResponse toResponse(Stock stock);
}
