package com.ptit.demo.Controller;

import com.ptit.demo.DTO.ProviderRequest;
import com.ptit.demo.Mapper.ProviderMapper;
import com.ptit.demo.Model.Providers;
import com.ptit.demo.DTO.ProviderResponse;
import com.ptit.demo.Service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/providers")
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderService providerService;
    private final ProviderMapper providerMapper;

    @PostMapping
    public ResponseEntity<ProviderResponse> createProvider(@RequestBody ProviderRequest request){
        Providers provider = providerService.createProvider(providerMapper.toProvider(request));
        return ResponseEntity.ok(providerMapper.toResponse(provider));
    }

    @GetMapping
    public ResponseEntity<List<ProviderResponse>> allProviders(){
        List<Providers> providers = providerService.getAllProviderActivating();
        List<ProviderResponse> responses = providerMapper.toResponse(providers);
        return ResponseEntity.ok(responses);
    }

    @GetMapping(value = "/contain-name")
    public ResponseEntity<List<ProviderResponse>> getProvidersContainName(@RequestParam String name){
        return ResponseEntity.ok(providerMapper.toResponse(providerService.findProviderByName(name)));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProviderResponse> updateProvider(@PathVariable Integer id, @RequestBody ProviderRequest request){
        return ResponseEntity.ok(providerMapper.toResponse(providerService.updateProvider(id, request)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteProvider(@PathVariable Integer id){
        return ResponseEntity.ok(providerService.softDeleteProvider(id));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProviderResponse> providerById(@PathVariable Integer id){
        return ResponseEntity.ok().body(providerMapper.toResponse(providerService.findById(id)));
    }
}
