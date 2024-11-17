package com.riyawansilver.repositories;

import com.riyawansilver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Ejb3TransactionAnnotationParser;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
}
