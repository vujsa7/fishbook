package com.fishbook.password.renewal.service.impl;

import com.fishbook.password.renewal.model.PasswordRenewalMark;
import com.fishbook.password.renewal.dao.PasswordRenewalMarkRepository;
import com.fishbook.password.renewal.service.PasswordRenewalMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordRenewalMarkServiceImpl implements PasswordRenewalMarkService {
    private final PasswordRenewalMarkRepository passwordRenewalMarkRepository;

    @Autowired
    public PasswordRenewalMarkServiceImpl(PasswordRenewalMarkRepository passwordRenewalMarkRepository){
        this.passwordRenewalMarkRepository = passwordRenewalMarkRepository;
    }

    @Override
    public Optional<PasswordRenewalMark> findPasswordRenewalMark(String username) {
        return passwordRenewalMarkRepository.findPasswordRenewalMarkByUsername(username);
    }

}
