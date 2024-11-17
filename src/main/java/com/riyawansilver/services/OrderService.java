package com.riyawansilver.services;

import com.riyawansilver.dtos.CartDto;
import com.riyawansilver.dtos.CartItemDto;
import com.riyawansilver.dtos.OrderDto;
import com.riyawansilver.dtos.OrderItemDto;
import com.riyawansilver.models.Order;
import com.riyawansilver.models.OrderItem;
import com.riyawansilver.models.Products;
import com.riyawansilver.models.User;
import com.riyawansilver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepo orderItemRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepository;


    public OrderDto createOrder(Long userId) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isEmpty()) throw new RuntimeException("User not found");

        // Fetch user's cart
        CartDto cartDto = cartService.viewCart(userId);
        if (cartDto.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty. Cannot create order.");
        }

        // Create a new order and set initial properties
        Order order = new Order();
        order.setUser(optionalUser.get());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");  // Initial status
        order.setTotalAmount(cartDto.getTotal());


//        Products product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Process each item in the cart and add it to the order as an OrderItem
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItemDto cartItemDto : cartDto.getItems()) {
            if(cartItemDto.isStockA()) {
                Optional<Products> product = productRepository.findById(cartItemDto.getProductId());
                if (product.isEmpty()) throw new RuntimeException("Product not found");
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setProduct(product.get()); // Assuming Products has an ID constructor
                orderItem.setQuantity(cartItemDto.getQuantity());
                orderItem.setPrice(cartItemDto.getPrice());
                orderItems.add(orderItem);
            }
        }

        order.setItems(orderItems);

        // Save order and items
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

//        // Clear cart after order is created
//        Cart cart = cartService.getCartByUser(optionalUser.get());
//        cart.getItems().clear();
//        cartRepository.save(cart);

        return convertToOrderDto(order);
    }




    public OrderDto convertToOrderDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setUserEmail(order.getUser().getEmail());
        dto.setAddress(order.getUser().getAddress());
        dto.setMobile(order.getUser().getMobile());
        dto.setUserName(order.getUser().getName());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setDelivery(order.isDelivery());

        // Call the new helper method to get the list of OrderItemDto
        dto.setItems(convertOrderItemsToDto(order.getItems()));

        return dto;
    }

    // New helper method to convert OrderItem list to OrderItemDto list
    private List<OrderItemDto> convertOrderItemsToDto(List<OrderItem> orderItems) {
        List<OrderItemDto> itemDtos = new ArrayList<>();
        for (OrderItem item : orderItems) {
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setId(item.getId());
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setProductName(item.getProduct().getName());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setPrice(item.getPrice());
            itemDto.setTotalPrice(item.getPrice() * item.getQuantity());
            itemDtos.add(itemDto);
        }
        return itemDtos;
    }


    public List<OrderDto> getUserOrders(Long userId) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isEmpty()) throw new RuntimeException("User not found");

        List<Order> orders = orderRepository.findByUser(optionalUser.get());
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            orderDtos.add(convertToOrderDto(order));
        }

        return orderDtos;
    }


    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToOrderDto(order);
    }

    public List<OrderDto> getAllOrder() {
        List<Order> orderList = orderRepository.findAll();

        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orderList) {
            orderDtos.add(convertToOrderDto(order));
        }
        return orderDtos;
    }

    public String updateDeliveryStatus(long id) {
        Optional<Order> optionalOrder=orderRepository.findById(id);

        if(optionalOrder.isEmpty())throw new RuntimeException("Order not found");

        Order order=optionalOrder.get();
        order.setDelivery(true);

        orderRepository.save(order);
        return "Order Deliverd Sucessfully";

    }

    public OrderDto buyNow(long userID, Long productId, int quantity) {

        User user = userRepo.findById(userID).orElseThrow(() -> new RuntimeException("User not found"));
        Products product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if enough stock is available
        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for this product.");
        }

        // Create new Order
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        // Create OrderItem for the Buy Now order
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        orderItem.setPrice(product.getPrice());

        // Add OrderItem to Order and calculate total amount
        order.getItems().add(orderItem);
        double totalAmount = quantity * product.getPrice();
        order.setTotalAmount(totalAmount);

        // Save the order
        return convertToOrderDto(orderRepository.save(order));

       // Update product stock
//        product.setQuantity(product.getQuantity() - quantity);
//        productRepository.save(product);


    }

    public String updateAddress(Long id, String address) {

        Optional<User> optionalUser=userRepo.findById(id);
        if(optionalUser.isEmpty())throw new RuntimeException("User not found");

        User user=optionalUser.get();
        user.setAddress(address);

        userRepo.save(user);
        return "User Address Changed";
    }
}

