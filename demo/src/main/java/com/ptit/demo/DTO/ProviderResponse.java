package com.ptit.demo.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProviderResponse {
    Integer id;
    String name;
    String headOfficeLocation;
    List<Integer> productsId;
}
