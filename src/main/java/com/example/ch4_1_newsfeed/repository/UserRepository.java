package com.example.ch4_1_newsfeed.repository;

import com.example.ch4_1_newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    
}
