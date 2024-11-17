package com.riyawansilver.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Products extends BaseModel {


    private String name;
    private String description;
    private double price;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Category category;
    private int quantity;

    private boolean available=true;

    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;


    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OrderItem> orderItems;
}
