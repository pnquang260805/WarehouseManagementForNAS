package com.ptit.demo.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockRequest {
    Integer productId;
    Integer warehouseId;
    Long quantity;
    String type;
}
