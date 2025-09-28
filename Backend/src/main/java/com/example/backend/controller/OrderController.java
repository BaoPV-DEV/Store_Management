package com.example.backend.controller;

import com.example.backend.dtos.request.orders.OrderRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    @PostMapping("")
    public ResponseEntity<?> createOrders(@RequestBody @Valid OrderRequestDto orderRequestDto, BindingResult result) {
        try{
            if (result.hasErrors()) {
                List<String> resultErrorList = result.getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(resultErrorList);
            }
            return ResponseEntity.ok("Successfully created categories!" + orderRequestDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrderByUserId(@Valid @PathVariable("user_id") Long userId) {
        try{
            return ResponseEntity.ok("OrderId = !" + userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{user_id}")
    // ADMIN
    public ResponseEntity<?> updateOrderByUserId(
            @Valid @PathVariable("user_id") Long userId,
            @Valid @RequestBody OrderRequestDto orderRequestDto,
            BindingResult result) {
        try{
            if (result.hasErrors()) {
                List<String> resultErrorList = result.getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(resultErrorList);
            }
            return ResponseEntity.ok(String.format("Update orderId = %d, new order = %s", userId, orderRequestDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{user_id}")
    // ADMIN
    public ResponseEntity<?> deleteOrderByUserId(
            @PathVariable("user_id") Long userId) {
        try{
            return ResponseEntity.ok("Delete orderId = " + userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
