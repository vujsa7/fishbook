package com.fishbook.registration.service.impl;

import com.fishbook.registration.dao.VerificationCodeRepository;
import com.fishbook.registration.model.VerificationCode;
import com.fishbook.registration.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;

    @Autowired
    public VerificationCodeServiceImpl(VerificationCodeRepository verificationCodeRepository){
        this.verificationCodeRepository = verificationCodeRepository;
    }

    @Override
    public VerificationCode findByVerificationCode(String verificationCode) {
        return verificationCodeRepository.findOneByVerificationCode(verificationCode);
    }
}
