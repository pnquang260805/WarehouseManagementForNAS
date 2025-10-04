package com.ptit.demo.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    String productName;
    String description;
    BigDecimal pricePerUnit;
    Integer providerId;
    String imgUrl;
}
