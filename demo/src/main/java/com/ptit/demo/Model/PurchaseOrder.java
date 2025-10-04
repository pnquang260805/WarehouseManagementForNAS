// Đơn nhập hàng từ nhà cung cấp

package com.ptit.demo.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "purchase_order")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Products products;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    Warehouse warehouse;

    Date purchaseDate;
    Long quantities;
}
