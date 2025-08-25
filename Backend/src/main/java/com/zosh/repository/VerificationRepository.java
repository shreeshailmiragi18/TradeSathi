package com.zosh.repository;

import com.zosh.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository extends JpaRepository<VerificationCode, Long> {
    VerificationCode findByAppuserId(Long userId);
}
