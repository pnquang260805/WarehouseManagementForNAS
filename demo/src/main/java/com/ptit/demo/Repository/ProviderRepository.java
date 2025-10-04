package com.ptit.demo.Repository;

import com.ptit.demo.Model.Providers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<Providers, Integer> {
    List<Providers> findByNameContainingIgnoreCase(String name);
    List<Providers> findAllByDeletedFalse();
}
