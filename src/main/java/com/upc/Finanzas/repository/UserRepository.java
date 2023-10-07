package com.upc.Finanzas.repository;

import com.upc.Finanzas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);
    boolean existsById(Long userId);
    boolean existsByEmail(String email);
    User findByEmail(String email);
    List<User>findAll();
}
