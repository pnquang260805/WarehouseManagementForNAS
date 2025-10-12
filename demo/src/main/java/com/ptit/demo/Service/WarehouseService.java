package com.ptit.demo.Service;

import com.ptit.demo.DTO.WarehouseRequest;
import com.ptit.demo.Mapper.WarehouseMapper;
import com.ptit.demo.Model.Warehouse;
import com.ptit.demo.Repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    public Warehouse createWarehouse(WarehouseRequest request){
        return warehouseRepository.save(warehouseMapper.toWarehouse(request));
    }

    public Warehouse searchById(Integer id){
        return warehouseRepository.findById(id).orElseThrow(()->new RuntimeException("Warehouse not found"));
    }

    public List<Warehouse> searchByLocation(String location){
        return warehouseRepository.findByLocationContainingIgnoreCase(location);
    }

    public List<Warehouse> allWarehouse(){
        return warehouseRepository.findAll();
    }

    public void deleteWarehouse(Integer id){
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(()->new RuntimeException("Warehouse not found"));
        warehouseRepository.delete(warehouse);
    }

    public Warehouse updateWarehouse(Integer id, WarehouseRequest request){
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(()->new RuntimeException("Warehouse not found"));
        warehouse.setLocation(request.getLocation());
        warehouse.setName(request.getName());
        return warehouseRepository.save(warehouse);
    }
}
