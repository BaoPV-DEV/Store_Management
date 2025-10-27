package com.example.backend.controller;

import com.example.backend.dtos.request.orders.OrderDetailRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders_details")
public class OrderDetailController {
    @PostMapping("")
    public ResponseEntity<?> createOrdersDetails(
            @RequestBody @Valid OrderDetailRequestDto orderDetailRequestDto,
            BindingResult result) {
        try{
            if (result.hasErrors()) {
                List<String> resultErrorList = result.getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(resultErrorList);
            }
            return ResponseEntity.ok("Successfully created order_detail!" + orderDetailRequestDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetailById(@Valid @PathVariable("id") Long orderDetailId) {
        try{
            return ResponseEntity.ok("Order_Detail ID = !" + orderDetailId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Lấy danh sách chi tiết theo orderId
     * @param orderDetailId
     * @return
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderByOrderId(@Valid @PathVariable("orderId") Long orderDetailId) {
        try{
            return ResponseEntity.ok("Order_Detail Order ID = !" + orderDetailId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    // ADMIN
    public ResponseEntity<?> updateOrderDetails(
            @Valid @PathVariable("id") Long id,
            @Valid @RequestBody OrderDetailRequestDto orderDetailRequestDto,
            BindingResult result) {
        try{
            if (result.hasErrors()) {
                List<String> resultErrorList = result.getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(resultErrorList);
            }
            return ResponseEntity.ok(String.format("Update order detail with id = %d, new order_detail = %s", id, orderDetailRequestDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    // ADMIN
    public ResponseEntity<?> deleteOrderDetail(
            @PathVariable("id") Long userId) {
        try{
            return ResponseEntity.ok("Delete orderId = " + userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
