package com.ptit.demo.Service;

import com.ptit.demo.DTO.ProductRequest;
import com.ptit.demo.Mapper.ProductMapper;
import com.ptit.demo.Model.Products;
import com.ptit.demo.Model.Providers;
import com.ptit.demo.Repository.ProductRepository;
import com.ptit.demo.Repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final ProviderRepository providerRepository;

    public Products createProduct(ProductRequest request){
        Products product = productMapper.toProduct(request);

        Providers provider = providerRepository.findById(request.getProviderId()).orElseThrow(() -> new RuntimeException("Provider not found"));
        product.setProviders(provider);
        provider.getProducts().add(product);

//        providerRepository.save(provider);
        return productRepository.save(product);
    }

    public List<Products> allProduct(){
        return productRepository.findAll();
    }

    public List<Products> productByName(String name){
        return productRepository.findByProductNameContainingIgnoreCase(name);
    }

    public Products updateProduct(Integer id, ProductRequest request){
        Products product = productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPricePerUnit(request.getPricePerUnit());
        product.setImgUrl(request.getImgUrl());

        Providers provider = providerRepository.findById(request.getProviderId()).orElseThrow(() -> new RuntimeException("Provider not found"));
        product.setProviders(provider);
        provider.getProducts().add(product);

        providerRepository.save(provider);
        return productRepository.save(product);
    }

    public void deleteProduct(Integer id){
        Products product = productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        productRepository.delete(product);
    }
}
