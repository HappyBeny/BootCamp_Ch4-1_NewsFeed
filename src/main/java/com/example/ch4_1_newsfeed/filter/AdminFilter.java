package com.example.ch4_1_newsfeed.filter;

import com.example.ch4_1_newsfeed.domain.UserType;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class AdminFilter implements Filter {

    // 필터 적용시키는 URI
    public String[] ADMIN_LIST = {"/admin", "/blockUser", "/"};

    /**
     * URI가 화이트리스트에 포함되어있는지 확인한다
     * 포함된 경우, 세션이 존재하는지와 세션에 "role" 속성이 있는지를 검사합니다.
     * 존재하는 role 속성의 값이 ADMIN이 아닌 경우 401(Unauthorized) 상태 코드로 응답합니다.
     */
    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();

        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (!isWhitelisted(uri)) {

            HttpSession session = request.getSession();

            if (session == null || session.getAttribute("role") == null ||
            !UserType.ADMIN.name().equals(session.getAttribute("role"))) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to access this resource");

            }

            chain.doFilter(request, response);
        }
    }

    public boolean isWhitelisted(String uri) {
        return PatternMatchUtils.simpleMatch(ADMIN_LIST, uri);
    }
}
