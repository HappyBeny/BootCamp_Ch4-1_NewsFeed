package com.example.ch4_1_newsfeed.controller;


import com.example.ch4_1_newsfeed.request.LoginRequest;
import com.example.ch4_1_newsfeed.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 로그인 기능
     * @param loginRequest : userEmail, userPassword
     * @param session
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequest loginRequest, HttpSession session) {

        UserDto userDto = userService.getUserId(loginRequest);

        session.setAttribute("userId", userDto.getId());

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


}
