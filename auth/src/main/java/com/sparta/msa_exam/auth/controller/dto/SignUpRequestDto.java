package com.sparta.msa_exam.auth.controller.dto;

import com.sparta.msa_exam.auth.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    private String username;

    private String password;

    private UserRole userRole;
}
