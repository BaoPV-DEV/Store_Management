package com.example.backend.repositories;

import com.example.backend.models.Order;
import com.example.backend.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    // Page<Product> findAll(Long userId, Pageable pageable);
}
