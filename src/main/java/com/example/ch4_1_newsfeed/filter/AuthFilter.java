package com.example.ch4_1_newsfeed.filter;

import com.example.ch4_1_newsfeed.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class AuthFilter implements Filter {

    // 필터 적용 안 시키는 URL
    public static final String[] WHITE_LIST = {"/", "/users/login", "/users/signup"};

    /**
     * URI가 화이트리스트에 포함되어있는지 확인한다
     * 포함되지 않은 경우, 세션이 존재하는지와 세션에 "username" 속성이 있는지를 검사합니다.
     * 유효하지 않은 요청은 401(Unauthorized) 상태 코드로 응답합니다.
     */
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (!isWhiteList(requestURI)) {

            HttpSession session = httpRequest.getSession(false);


            if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "unauthorized");
            }
        }

        filterChain.doFilter(request, response);
    }

    public boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}

