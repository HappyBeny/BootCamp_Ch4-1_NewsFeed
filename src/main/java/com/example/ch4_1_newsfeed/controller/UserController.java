package com.example.ch4_1_newsfeed.controller;


import com.example.ch4_1_newsfeed.common.SessionConst;
import com.example.ch4_1_newsfeed.model.dto.user.request.*;
import com.example.ch4_1_newsfeed.model.dto.user.response.RelationshipResponseDto;
import com.example.ch4_1_newsfeed.model.dto.user.response.SignUpUserResponseDto;
import com.example.ch4_1_newsfeed.model.dto.user.response.UpdateUserResponseDto;
import com.example.ch4_1_newsfeed.model.dto.user.response.UserResponseDto;
import com.example.ch4_1_newsfeed.service.AuthService;
import com.example.ch4_1_newsfeed.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    /**
     * 로그인 기능
     *
     * @param loginRequest : userEmail, userPassword
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@Valid @RequestBody LoginUserRequestDto loginRequest,
                                                 HttpServletRequest servletRequest,
                                                 HttpServletResponse servletResponse) {

        UserResponseDto userDto = userService.loginUser(loginRequest);
        authService.setSessionAndCookie(userDto, servletRequest, servletResponse);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * 로그아웃 기능
     * @param session
     * @param response
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session, HttpServletResponse response) {

        // 세션 무효화
        session.invalidate();

        // 쿠키 삭제
        Cookie cookie = new Cookie(SessionConst.LOGIN_USER, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return new ResponseEntity<>("Logout Done", HttpStatus.OK);
    }

    /**
     * 회원가입 기능
     * @param signUpRequest
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<SignUpUserResponseDto> createUser(@Valid @RequestBody SignUpUserRequestDto signUpRequest) {
        SignUpUserResponseDto userSignUpDto = userService.createUser(signUpRequest);

        return new ResponseEntity<>(userSignUpDto, HttpStatus.OK);
    }

    /**
     * 내 프로필 수정
     */
    @PutMapping("/me")
    public ResponseEntity<UpdateUserResponseDto> updateMyProfile(
            HttpSession session, @Valid @RequestBody UpdateUserRequestDto request
    ) {
        Long userId = (Long) session.getAttribute(SessionConst.LOGIN_USER);
        UpdateUserResponseDto userUpdateDto = userService.updateMyProfile(userId, request);

        return new ResponseEntity<>(userUpdateDto, HttpStatus.OK);
    }

    /**
     * 비밀번호 수정
     */
    @PutMapping("/me/password")
    public ResponseEntity<Void> updateMyPassword(
            HttpSession session, @Valid @RequestBody UpdatePasswordUserRequestDto request
    ) {
        Long userId = (Long) session.getAttribute(SessionConst.LOGIN_USER);
        userService.updateMyPassword(userId, request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/me")
    public ResponseEntity<String> deleteUser(@Valid @ModelAttribute DeleteUserRequestDto request, HttpSession session, HttpServletResponse response) {
        Long userId = (Long) session.getAttribute(SessionConst.LOGIN_USER);
        userService.deleteUser(userId, request);

        // 세션 무효화
        session.invalidate();

        // 쿠키 삭제
        Cookie cookie = new Cookie(SessionConst.LOGIN_USER, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok("성공적으로 탈퇴 되었습니다.");
    }


    /**
     * 팔로우/언팔로우
     */
    @PostMapping("/{id}")
    public ResponseEntity<RelationshipResponseDto> follow(
            @Valid @NotNull(message = "id가 포함되어야 합니다.") @Positive(message = "id는 양의 정수여야 합니다.") @PathVariable Long id,
            HttpSession session
    ) {

        RelationshipResponseDto following = userService.follow(id, session);

        return new ResponseEntity<>(following, HttpStatus.OK);
    }
}
