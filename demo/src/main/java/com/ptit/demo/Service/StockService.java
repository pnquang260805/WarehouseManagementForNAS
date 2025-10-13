package com.ptit.demo.Service;

import com.ptit.demo.DTO.StockRequest;
import com.ptit.demo.Mapper.WarehouseMapper;
import com.ptit.demo.Model.Products;
import com.ptit.demo.Model.Stock;
import com.ptit.demo.Model.Warehouse;
import com.ptit.demo.Repository.ProductRepository;
import com.ptit.demo.Repository.StockRepository;
import com.ptit.demo.Repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    public Stock createStock(StockRequest request){
        Products product = productRepository.findById(request.getProductId()).orElseThrow(()->new RuntimeException("Product not found"));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId()).orElseThrow(()->new RuntimeException("Warehouse not found"));

        Stock stock = Stock.builder().type(request.getType()).product(product).warehouse(warehouse).quantity(request.getQuantity()).build();
        return stockRepository.save(stock);
    }

    public List<Stock> findAllStock(){
        return stockRepository.findAll();
    }

    public Stock findById(Integer id){
        return stockRepository.findById(id).orElseThrow(()->new RuntimeException("Stock not found"));
    }

    public Stock updateStock(Integer id, StockRequest request){
        Stock stock = stockRepository.findById(id).orElseThrow(()->new RuntimeException("Stock not found"));
        Products product = productRepository.findById(request.getProductId()).orElseThrow(()->new RuntimeException("Product not found"));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId()).orElseThrow(()->new RuntimeException("Warehouse not found"));
        stock.setType(request.getType());
        stock.setProduct(product);
        stock.setWarehouse(warehouse);
        stock.setQuantity(request.getQuantity());
        return stockRepository.save(stock);
    }

    public void deleteStock(Integer id){
        Stock stock = stockRepository.findById(id).orElseThrow(()->new RuntimeException("Stock not found"));
        Products product = productRepository.findById(stock.getProduct().getId()).orElseThrow(()->new RuntimeException("Product not found"));
        Warehouse warehouse = warehouseRepository.findById(stock.getWarehouse().getId()).orElseThrow(()->new RuntimeException("Warehouse not found"));

        product.getStocks().remove(stock);
        warehouse.getStocks().remove(stock);
        stockRepository.delete(stock);
    }
}
