package com.ptit.demo.Service;

import com.ptit.demo.DTO.ProviderRequest;
import com.ptit.demo.Model.Providers;
import com.ptit.demo.Repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProviderService {
    private final ProviderRepository providerRepository;

    public Providers createProvider(Providers data){
        Providers savedProvider = providerRepository.save(data);
        return savedProvider;
    }

    public List<Providers> getAllProviderActivating(){
        return providerRepository.findAllByDeletedFalse();
    }

    public List<Providers> findProviderByName(String name){
        return providerRepository.findByNameContainingIgnoreCase(name);
    }

    public Providers findById(Integer id){
        return providerRepository.findById(id).orElseThrow(() -> new RuntimeException("Provider not found"));
    }

    public Providers updateProvider(Integer id, ProviderRequest request){
        Providers provider = providerRepository.findById(id).orElseThrow(()->new RuntimeException("Provider not found"));
        provider.setName(request.getName());
        provider.setHeadOfficeLocation(request.getHeadOfficeLocation());
        providerRepository.save(provider);
        return provider;
    }

    public Boolean softDeleteProvider(Integer id){
        Providers provider = providerRepository.findById(id).orElseThrow(()->new RuntimeException("Provider not found"));
        provider.setDeleted(true);
        providerRepository.save(provider);
        return true;
    }
}
