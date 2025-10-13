package com.ptit.demo.Repository;

import com.ptit.demo.Model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository  extends JpaRepository<Stock, Integer> {
}
