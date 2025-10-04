package com.ptit.demo.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "providers")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Providers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    String headOfficeLocation;
    // mappedBy: bên nào là chủ sở hữu của mối quan hệ (không sở hữu FK)
    // Cascade cho phép bạn tự động áp dụng các hành động (như save, delete, merge, v.v.) từ entity cha sang entity con.
    @Builder.Default
    @OneToMany(mappedBy = "providers")
    List<Products> products = new ArrayList<>();

    @Builder.Default
    Boolean deleted = false;
}
