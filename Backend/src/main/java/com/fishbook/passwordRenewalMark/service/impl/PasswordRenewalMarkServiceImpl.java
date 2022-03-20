package com.fishbook.passwordRenewalMark.service.impl;

import com.fishbook.passwordRenewalMark.model.PasswordRenewalMark;
import com.fishbook.passwordRenewalMark.dao.PasswordRenewalMarkRepository;
import com.fishbook.passwordRenewalMark.service.PasswordRenewalMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordRenewalMarkServiceImpl implements PasswordRenewalMarkService {
    private final PasswordRenewalMarkRepository passwordRenewalMarkRepository;

    @Autowired
    public PasswordRenewalMarkServiceImpl(PasswordRenewalMarkRepository passwordRenewalMarkRepository){
        this.passwordRenewalMarkRepository = passwordRenewalMarkRepository;
    }

    @Override
    public void markUserForPasswordRenewal(String username) {
        passwordRenewalMarkRepository.save(new PasswordRenewalMark(username));
    }

    @Override
    public void unmarkUserForPasswordRenewal(String username) {
        passwordRenewalMarkRepository.deletePasswordRenewalMarkByUsername(username);
    }
}
