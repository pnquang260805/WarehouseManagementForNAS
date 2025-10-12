package com.ptit.demo.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "warehouse")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;
    String location;

    @Builder.Default
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    List<Stock> stocks = new ArrayList<>();
}
