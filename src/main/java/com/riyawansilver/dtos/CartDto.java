package com.riyawansilver.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class CartDto {
    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private List<CartItemDto> items;
    private double total;

    // Getters and setters
}