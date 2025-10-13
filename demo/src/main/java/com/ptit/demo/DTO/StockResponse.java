package com.ptit.demo.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockResponse {
    Integer id;
    Integer productId;
    Integer warehouseId;
    Long quantity;
    String type;
    LocalDate transactionDate;
}
