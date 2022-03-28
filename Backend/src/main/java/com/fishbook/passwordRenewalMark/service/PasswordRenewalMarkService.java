package com.fishbook.passwordRenewalMark.service;

import com.fishbook.passwordRenewalMark.model.PasswordRenewalMark;

import java.util.Optional;

public interface PasswordRenewalMarkService {
    Optional<PasswordRenewalMark> findPasswordRenewalMark(String username);
}
