package com.ptit.demo.Repository;

import com.ptit.demo.Model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository  extends JpaRepository<Warehouse, Integer> {
    List<Warehouse> findByLocationContainingIgnoreCase(String location);
}
