package com.riyawansilver.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    private Long id;
    private Long productId;
    private String productName;
    private int quantity;
    private double price;
    private double totalPrice;
    private boolean StockA;

    // Getters and setters
}