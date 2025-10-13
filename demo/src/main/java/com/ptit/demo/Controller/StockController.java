package com.ptit.demo.Controller;

import com.ptit.demo.DTO.StockRequest;
import com.ptit.demo.DTO.StockResponse;
import com.ptit.demo.Mapper.StockMapper;
import com.ptit.demo.Model.Stock;
import com.ptit.demo.Service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {
    private final StockMapper stockMapper;
    private final StockService stockService;

    @PostMapping
    public ResponseEntity<StockResponse> createStock(@RequestBody StockRequest request){
        return ResponseEntity.ok(stockMapper.toResponse(stockService.createStock(request)));
    }

    @GetMapping
    public ResponseEntity<List<StockResponse>> allStock(){
        List<Stock> stocks = stockService.findAllStock();
        List<StockResponse> responses = stocks.stream().map(stockMapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponse> findById(@PathVariable Integer id){
        return ResponseEntity.ok(stockMapper.toResponse(stockService.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteStock(@PathVariable Integer id){
        stockService.deleteStock(id);
        return ResponseEntity.ok(true);
    }
}
