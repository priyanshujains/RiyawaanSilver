package com.riyawansilver.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
    private Long id;
    private Long productId;
    private String productName;
    private int quantity;
    private double price;
    private double totalPrice;



    // Getters and Setters
}


