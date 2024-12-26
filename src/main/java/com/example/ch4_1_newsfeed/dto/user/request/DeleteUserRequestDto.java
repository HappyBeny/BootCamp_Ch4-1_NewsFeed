package com.example.ch4_1_newsfeed.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeleteUserRequestDto {
    @NotBlank(message = "비밀번호가 포함되어야 합니다.")
    @Size(max = 255, message = "비밀번호는 255 이하여야 합니다.")
    private final String password;
}
