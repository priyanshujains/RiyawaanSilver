package com.riyawansilver.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class GetProductDto {
    private String name;
    private String description;
    private double price;

    private String category;
    private int quantity;
    private boolean available;

        private String imageName;
      private String imageType;
      private byte[] imageData;

}
