package com.riyawansilver.controllers;

import com.riyawansilver.security.CustomUserDetail;
import com.riyawansilver.dtos.CartDto;
import com.riyawansilver.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Endpoint to view the cart by userId
    @GetMapping()
    public ResponseEntity<CartDto> viewCart() {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CartDto cartDto = cartService.viewCart(userDetails.getUserID());
        return ResponseEntity.ok(cartDto);
    }

    // Endpoint to add an item to the cart
    @PostMapping()
    public ResponseEntity<CartDto> addItemToCart(@RequestParam Long productId) {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CartDto cartDto = cartService.addItemToCart(userDetails.getUserID(), productId);
        return ResponseEntity.ok(cartDto);
    }

    // Endpoint to remove an item from the cart
    @DeleteMapping()
    public ResponseEntity<CartDto> removeItemFromCart(@RequestParam Long productId) {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CartDto cartDto = cartService.removeItemFromCart(userDetails.getUserID(), productId);
        return ResponseEntity.ok(cartDto);
    }

    @PutMapping()
    public ResponseEntity<CartDto> updateItemQuantity(@RequestParam Long productId, @RequestParam int quantity) {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CartDto cartDto = cartService.updateItemQuantity(userDetails.getUserID(), productId, quantity);
        return ResponseEntity.ok(cartDto);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userDetails);
    }
}
