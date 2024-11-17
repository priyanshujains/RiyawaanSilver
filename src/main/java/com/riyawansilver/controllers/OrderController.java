package com.riyawansilver.controllers;

import com.riyawansilver.security.CustomUserDetail;
import com.riyawansilver.dtos.OrderDto;
import com.riyawansilver.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;



    @PostMapping()
    public ResponseEntity<OrderDto> createOrder() {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        OrderDto orderDto = orderService.createOrder(userDetails.getUserID());
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping()
    public ResponseEntity<?> getOrderOfUser(){
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new ResponseEntity<>(orderService.getUserOrders(userDetails.getUserID()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id){
        return new ResponseEntity<>(orderService.getOrderById(id),HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllOrder(){
        return new ResponseEntity<>(orderService.getAllOrder(),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDeliveryStatus(@PathVariable long id){
        return new ResponseEntity<>(orderService.updateDeliveryStatus(id),HttpStatus.OK);
    }


    @PostMapping("/buy-now")
    public OrderDto buyNow(@RequestParam Long productId, @RequestParam int quantity) {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderService.buyNow(userDetails.getUserID(), productId, quantity);
    }

    @PutMapping()
    public ResponseEntity<?> updateAddress(@RequestParam String address){
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(orderService.updateAddress(userDetails.getUserID(),address),HttpStatus.OK);
    }
}
