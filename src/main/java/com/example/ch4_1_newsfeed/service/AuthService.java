package com.example.ch4_1_newsfeed.service;

import com.example.ch4_1_newsfeed.SessionConst;
import com.example.ch4_1_newsfeed.dto.user.response.UserResponseDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public void setSessionAndCookie(UserResponseDto dto,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        //세션 설정
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, dto.getId());

        //쿠키 설정
        Cookie cookie = new Cookie("session_id", session.getId());
        cookie.setHttpOnly(true); //자바스크립트를 통해 쿠키에 접근하지 못하도록 방지(XSS)
        cookie.setSecure(true); //https 연결일 때만 쿠키 전송, http는 전송 x
        response.addCookie(cookie);
    }
}
