package com.sparta.msa_exam.auth.service;

import com.sparta.msa_exam.auth.common.exception.CustomException;
import com.sparta.msa_exam.auth.common.exception.ErrorCode;
import com.sparta.msa_exam.auth.config.JwtProvider;
import com.sparta.msa_exam.auth.controller.dto.SignInRequestDto;
import com.sparta.msa_exam.auth.controller.dto.SignUpRequestDto;
import com.sparta.msa_exam.auth.entity.User;
import com.sparta.msa_exam.auth.repository.AuthRepository;
import com.sparta.msa_exam.auth.vo.Username;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    @Transactional
    public void signUp(SignUpRequestDto requestDto) {
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = User.builder()
                .username(Username.of(requestDto.getUsername()))
                .password(encodedPassword)
                .userRole(requestDto.getUserRole())
                .build();

        authRepository.save(user);
    }

    public String signIn(SignInRequestDto requestDto) {
        User user = getUser(requestDto);

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_DOES_NOT_MATCH);
        }
        return jwtProvider.generateToken(user);
    }


    @Transactional
    public void changeRoles(Long targetUserId, String token) {
        String accessToken = token.substring(7);
        if (!jwtProvider.isAdmin(accessToken)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        User user = getUser(targetUserId);

        user.changeRole();
    }

    public Boolean validateToken(String token) {
        validateTokenFormat(token);
        String accessToken = token.substring(7);

        if (!jwtProvider.validateToken(accessToken)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
        return true;
    }

    public Boolean validateAdminToken(String token) {
        validateTokenFormat(token);
        String accessToken = token.substring(7);

        if (!jwtProvider.isAdmin(accessToken)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
        return true;
    }

    private void validateTokenFormat(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    private User getUser(Long targetUserId) {
        return authRepository.findById(targetUserId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    private User getUser(SignInRequestDto requestDto) {
        Username username = Username.of(requestDto.getUsername());
        return authRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
