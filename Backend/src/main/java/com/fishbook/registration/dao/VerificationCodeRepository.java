package com.fishbook.registration.dao;

import com.fishbook.registration.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    VerificationCode findOneByVerificationCode(String verificationCode);
}
