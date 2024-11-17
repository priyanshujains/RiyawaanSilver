package com.riyawansilver.repositories;

import com.riyawansilver.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Products,Long> {

    @Override
    List<Products> findAll();
}
