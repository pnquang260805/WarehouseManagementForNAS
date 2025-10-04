package com.ptit.demo.Controller;

import com.ptit.demo.DTO.ProductRequest;
import com.ptit.demo.DTO.ProductsResponse;
import com.ptit.demo.Mapper.ProductMapper;
import com.ptit.demo.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductMapper productMapper;
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductsResponse> createProduct(@RequestBody ProductRequest request){
        return ResponseEntity.ok(productMapper.toResponse(productService.createProduct(request)));
    }

    @GetMapping
    public ResponseEntity<List<ProductsResponse>> allProduct(){
        return ResponseEntity.ok(productMapper.toResponse(productService.allProduct()));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductsResponse>> productByName(@RequestParam String name){
        return ResponseEntity.ok(productMapper.toResponse(productService.productByName(name)));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteProduct(@PathVariable Integer id){
        productService.deleteProduct(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductsResponse> updateProduct(@PathVariable Integer id, @RequestBody ProductRequest request){
        return ResponseEntity.ok(productMapper.toResponse(productService.updateProduct(id, request)));
    }
}
