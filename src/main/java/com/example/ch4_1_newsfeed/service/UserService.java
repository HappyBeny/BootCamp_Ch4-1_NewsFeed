package com.example.ch4_1_newsfeed.service;

import com.example.ch4_1_newsfeed.controller.UserDto;
import com.example.ch4_1_newsfeed.entity.User;
import com.example.ch4_1_newsfeed.repository.UserRepository;
import com.example.ch4_1_newsfeed.request.LoginRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto getUserId(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        // 비밀번호 암호화 된걸로 매치 예정

        return UserDto.from(user);
    }
}
