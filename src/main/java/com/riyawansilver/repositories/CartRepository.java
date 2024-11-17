package com.riyawansilver.repositories;

import com.riyawansilver.models.Cart;
import com.riyawansilver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Optional<Cart> findByUser(User user);
}
