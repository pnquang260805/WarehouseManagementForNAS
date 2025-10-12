package com.ptit.demo.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Table(name = "products")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String productName;
    String description;
    BigDecimal pricePerUnit;
    String imgUrl;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    Providers providers;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<Stock> stocks = new ArrayList<>();
}
