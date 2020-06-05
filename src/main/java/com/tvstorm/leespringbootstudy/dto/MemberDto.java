package com.tvstorm.leespringbootstudy.dto;

import com.tvstorm.leespringbootstudy.domain.entity.MemberEntity;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {
    private Long userNo;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .userNo(userNo)
                .email(email)
                .password(password)
                .build();
    }

//    @Builder
//    public MemberDto(Long id, String email, String password) {
//        this.userNo = id;
//        this.email = email;
//        this.password = password;
//    }
}
