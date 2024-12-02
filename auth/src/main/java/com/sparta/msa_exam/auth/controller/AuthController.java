package com.sparta.msa_exam.auth.controller;

import com.sparta.msa_exam.auth.controller.dto.SignInRequestDto;
import com.sparta.msa_exam.auth.controller.dto.SignUpRequestDto;
import com.sparta.msa_exam.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(
            @RequestBody SignUpRequestDto requestDto
    ) {
        authService.signUp(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입 완료");
    }

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(
            @RequestBody SignInRequestDto requestDto
    ) {
        String token = authService.signIn(requestDto);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token)
                .body("로그인 성공");
    }
}
