package com.ptit.demo.Controller;

import com.ptit.demo.DTO.WarehouseRequest;
import com.ptit.demo.DTO.WarehouseResponse;
import com.ptit.demo.Mapper.WarehouseMapper;
import com.ptit.demo.Service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    private final WarehouseMapper warehouseMapper;
    private final WarehouseService warehouseService;

    @GetMapping
    ResponseEntity<List<WarehouseResponse>> allWarehouse(){
        return ResponseEntity.ok(warehouseMapper.toResponse(warehouseService.allWarehouse()));
    }

    @GetMapping("/{id}")
    ResponseEntity<WarehouseResponse> warehouseById(@PathVariable Integer id){
        return ResponseEntity.ok(warehouseMapper.toResponse(warehouseService.searchById(id)));
    }

    @PostMapping
    ResponseEntity<WarehouseResponse> warehouseById(@RequestBody WarehouseRequest request){
        return ResponseEntity.ok(warehouseMapper.toResponse(warehouseService.createWarehouse(request)));
    }

    @GetMapping("/{loc}")
    ResponseEntity<List<WarehouseResponse>> warehouseByLoc(@PathVariable String loc){
        return ResponseEntity.ok(warehouseMapper.toResponse(warehouseService.searchByLocation(loc)));
    }

    @PutMapping("/{id}")
    ResponseEntity<WarehouseResponse> updateWarehouse(@PathVariable Integer id ,@RequestBody WarehouseRequest request){
        return ResponseEntity.ok(warehouseMapper.toResponse(warehouseService.updateWarehouse(id, request)));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> deleteWarehouse(@PathVariable Integer id){
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.ok(true);
    }
}
