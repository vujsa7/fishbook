package com.fishbook.registration.service;

import com.fishbook.registration.model.VerificationCode;

public interface VerificationCodeService {
    VerificationCode findByVerificationCode(String verificationCode);
}
