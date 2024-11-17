package com.riyawansilver.services;

import com.riyawansilver.dtos.CartDto;
import com.riyawansilver.dtos.CartItemDto;
import com.riyawansilver.models.Cart;
import com.riyawansilver.models.CartItem;
import com.riyawansilver.models.Products;
import com.riyawansilver.models.User;
import com.riyawansilver.repositories.CartItemRepository;
import com.riyawansilver.repositories.CartRepository;
import com.riyawansilver.repositories.ProductRepo;
import com.riyawansilver.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepo productRepository;

    @Autowired
    private UserRepo userRepo;


    public CartDto addItemToCart(Long userID, Long productId) {
        Optional<User> optionalUser=userRepo.findById(userID);
        if(optionalUser.isEmpty())throw new RuntimeException("user not found");
        Cart cart = getCartByUser(optionalUser.get());
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem existingCartItem = null;

        // Check if the item already exists in the cart
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                existingCartItem = item;
                break;
            }
        }

        if (existingCartItem == null) {
            // If the item is not in the cart, create a new CartItem
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setPrice(product.getPrice());
            cartItem.setStockA(product.getQuantity()>0);
            cart.getItems().add(cartItem);

        } else {
            // If it already exists, update the quantity
            if(product.getQuantity()> existingCartItem.getQuantity()) {
                existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            }

        }

        cartRepository.save(cart);
        return viewCart(userID); // Return updated cart as CartDto
    }

    public Cart getCartByUser(User user) {
        Optional<Cart> existingCart = cartRepository.findByUser(user);
        if (existingCart.isPresent()) {
            return existingCart.get();
        } else {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        }
    }

    public CartDto viewCart(Long userID) {

        Optional<User> optionalUser=userRepo.findById(userID);
        if(optionalUser.isEmpty())throw new RuntimeException("user not found");
        Cart cart = getCartByUser(optionalUser.get());


        // Create CartDto and map only required fields
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setUserId(cart.getUser().getId());
        cartDto.setUserName(cart.getUser().getName());
        cartDto.setUserEmail(cart.getUser().getEmail());

        List<CartItemDto> itemDtos = new ArrayList<>();
        double total = 0.0;

        for (CartItem item : cart.getItems()) {
            CartItemDto itemDto = new CartItemDto();
            itemDto.setId(item.getId());
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setProductName(item.getProduct().getName());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setPrice(item.getPrice());
            itemDto.setStockA(item.isStockA());
            itemDto.setTotalPrice(item.getQuantity() * item.getPrice());

            if(item.isStockA()) {
                total += itemDto.getTotalPrice();
            }
            itemDtos.add(itemDto);
        }

        cartDto.setItems(itemDtos);
        cartDto.setTotal(total);

        return cartDto;
    }


    public CartDto removeItemFromCart(Long userID, Long productId) {
        Optional<User> optionalUser=userRepo.findById(userID);
        if(optionalUser.isEmpty())throw new RuntimeException("user not found");
        Cart cart = getCartByUser(optionalUser.get());

        // Use an iterator to safely remove items while iterating
        Iterator<CartItem> iterator = cart.getItems().iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if (item.getProduct().getId().equals(productId)) {
                iterator.remove();
                cartItemRepository.delete(item); // Ensure the item is removed from the database
            }
        }

        cartRepository.save(cart);
        return viewCart(userID); // Return updated cart as CartDto
    }


    public CartDto updateItemQuantity(Long userID, Long productId, int quantity) {
        Optional<User> optionalUser=userRepo.findById(userID);
        if(optionalUser.isEmpty())throw new RuntimeException("user not found");
        Cart cart = getCartByUser(optionalUser.get());

        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));


        CartItem cartItem = null;
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                cartItem = item;
                break;
            }
        }

        if (cartItem == null) {
            throw new RuntimeException("Product not found in cart.");
        }

        if (quantity <= 0) {
            // If the quantity is set to 0 or below, remove the item from the cart
            cart.getItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
        }else if(quantity> product.getQuantity()){
                   cartItem.setStockA(false);
        }else {
            // Update the quantity
            cartItem.setQuantity(quantity);
        }

        cartRepository.save(cart);
        return viewCart(userID); // Return updated cart as CartDto
    }


}
