package com.ptit.demo.Mapper;

import com.ptit.demo.DTO.ProviderRequest;
import com.ptit.demo.Model.Providers;
import com.ptit.demo.DTO.ProviderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProviderMapper {
    // Dùng lambda funtion
    @Mapping(target = "productsId", expression = "java(provider.getProducts().stream().map(p -> p.getId()).toList())")
    ProviderResponse toResponse(Providers provider);
    List<ProviderResponse> toResponse(List<Providers> providers);

    Providers toProvider(ProviderRequest request);
}
