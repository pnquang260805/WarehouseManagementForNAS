package com.ptit.demo.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductsResponse {
    Integer id;
    String productName;
    String description;
    String imgUrl;
    BigDecimal pricePerUnit;
    Integer providerId;
    List<Integer> purchaseOrdersId;
}
