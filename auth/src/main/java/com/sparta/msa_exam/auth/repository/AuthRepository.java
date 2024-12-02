package com.sparta.msa_exam.auth.repository;

import com.sparta.msa_exam.auth.entity.User;
import com.sparta.msa_exam.auth.vo.Username;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(Username username);
}
