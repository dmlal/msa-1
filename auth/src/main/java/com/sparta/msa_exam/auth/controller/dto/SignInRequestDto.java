package com.sparta.msa_exam.auth.controller.dto;

import com.sparta.msa_exam.auth.vo.Username;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequestDto {

    private String username;
    private String password;
}
