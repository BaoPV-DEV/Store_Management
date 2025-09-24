package com.example.backend.repositories;

import com.example.backend.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> getOrderDetailById(Long id);
}
