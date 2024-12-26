package com.example.ch4_1_newsfeed.controller;


import com.example.ch4_1_newsfeed.dto.user.response.*;
import com.example.ch4_1_newsfeed.dto.user.request.*;
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
import org.springframework.validation.annotation.Validated;
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

    @PostMapping("/signup")
    public ResponseEntity<SignUpUserResponseDto> createUser(@Valid @RequestBody SignUpUserRequestDto signUpRequest) {
        SignUpUserResponseDto userSignUpDto = userService.createUser(signUpRequest);

        return new ResponseEntity<>(userSignUpDto, HttpStatus.OK);
    }

    /**
     * 내 프로필 조회
     */
    @GetMapping("/me")
    public ResponseEntity<ProfileUserResponseDto> getMyProfile(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        ProfileUserResponseDto myProfile = userService.getMyProfile(userId);

        return new ResponseEntity<>(myProfile, HttpStatus.OK);
    }

    /**
     * 내 프로필 수정
     */
    @PutMapping("/me")
    public ResponseEntity<UpdateUserResponseDto> updateMyProfile(
        HttpSession session, @Valid @RequestBody UpdateUserRequestDto request
    ) {
        Long userId = (Long) session.getAttribute("userId");
        UpdateUserResponseDto userUpdateDto = userService.updateMyProfile(userId, request);

        return new ResponseEntity<>(userUpdateDto, HttpStatus.OK);
    }

    /**
     * 비밀번호 수정
     */
    @PutMapping("/me/password")
    public ResponseEntity<Void> updateMyPassword(
        HttpSession session, @Valid @ModelAttribute UpdatePasswordUserRequestDto request
    ) {
        Long userId = (Long) session.getAttribute("userId");
        userService.updateMyPassword(userId, request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 다른 유저 프로필 조회
     */

    @GetMapping("/{id}")
    @Validated
    public ResponseEntity<ProfileUserResponseDto> getUserProfile(
        @PathVariable @NotNull(message = "id가 포함되어야 합니다.") @Positive(message = "id는 양의 정수여야 합니다.") Long id
    ) {
        ProfileUserResponseDto userProfile = userService.getUserProfile(id);

        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/me")
    public ResponseEntity<String> deleteUser(@Valid @ModelAttribute DeleteUserRequestDto request, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        userService.deleteUser(userId, request);

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
