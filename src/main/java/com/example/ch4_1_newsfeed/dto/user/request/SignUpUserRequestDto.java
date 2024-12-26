package com.example.ch4_1_newsfeed.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpUserRequestDto {
    @NotBlank(message = "이메일이 포함되어야 합니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호가 포함되어야 합니다.")
    @Size(max = 255, message = "비밀번호는 255 이하여야 합니다.")
    private String password;

    @NotBlank(message = "이름이 포함되어야 합니다.")
    @Size(max = 255, message = "이름은 255자 이하여야 합니다.")
    private String name;

    @NotBlank(message = "전화번호가 포함되어야 합니다.")
    @Pattern(regexp = "^(01[0-9])-(\\d{3,4})-(\\d{4})$", message = "올바른 전화번호 형식이어야 합니다.")
    private String phoneNumber;

    // 요청에서 바로 이미지 경로를 받을 것인지 이미지 파일을 받아 저장경로를 따로 지정할 것인지 논의 필요
    private String pictureUrl;
}
