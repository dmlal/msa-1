package com.sparta.msa_exam.auth.service;

import com.sparta.msa_exam.auth.controller.dto.SignUpRequestDto;
import com.sparta.msa_exam.auth.entity.User;
import com.sparta.msa_exam.auth.entity.enums.UserRole;
import com.sparta.msa_exam.auth.repository.AuthRepository;
import com.sparta.msa_exam.auth.vo.Username;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;


    @Transactional
    public void signUp(SignUpRequestDto requestDto) {
        User user = User.builder()
                .username(Username.of(requestDto.getUsername()))
                .password(requestDto.getPassword())
                .userRole(requestDto.getUserRole())
                .build();

        authRepository.save(user);
    }

}
