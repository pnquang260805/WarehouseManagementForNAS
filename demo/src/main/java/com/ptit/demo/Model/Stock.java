package com.ptit.demo.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "providers")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    // Liên kết đến Product (ManyToOne)
    @ManyToOne
    @JoinColumn(name = "product_id")
    Products product;

    // Liên kết đến Warehouse (ManyToOne)
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    Warehouse warehouse;

    Long quantity;
    String type;

    @Builder.Default
    LocalDate transactionDate = LocalDate.now();
}
