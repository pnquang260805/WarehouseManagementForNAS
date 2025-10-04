package com.ptit.demo.Repository;

import com.ptit.demo.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {
    List<Products> findByProductNameContainingIgnoreCase(String name);
}
