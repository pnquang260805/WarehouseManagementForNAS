package com.ptit.demo.Mapper;

import com.ptit.demo.DTO.WarehouseRequest;
import com.ptit.demo.DTO.WarehouseResponse;
import com.ptit.demo.Model.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {
    @Mapping(target = "stockIds", expression = "java(warehouse.getStocks().stream().map(p -> p.getId()).toList())")
    WarehouseResponse toResponse(Warehouse warehouse);
    List<WarehouseResponse> toResponse(List<Warehouse> warehouse);

    Warehouse toWarehouse(WarehouseRequest request);
}
