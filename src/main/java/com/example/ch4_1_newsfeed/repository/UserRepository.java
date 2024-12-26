package com.example.ch4_1_newsfeed.repository;

import com.example.ch4_1_newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findUserByName(String username);

    default User findUserByUsernameOrElseThrow(String username) {
        return findUserByName(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist username = " + username));
    }

    /**
     * null 예외를 처리하기 위해 Optional을 추가함
     */
    Optional<User> findByEmail(String email);

    
}
