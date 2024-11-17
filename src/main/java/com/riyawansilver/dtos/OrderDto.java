package com.riyawansilver.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class OrderDto{
    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private String address;
    private String mobile;
    private List<OrderItemDto> items; // List of order items
    private LocalDateTime orderDate;
    private double totalAmount;
    private String status;
    private boolean delivery;

    // Getters and Setters
}
