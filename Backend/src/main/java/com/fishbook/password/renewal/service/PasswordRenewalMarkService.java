package com.fishbook.password.renewal.service;

import com.fishbook.password.renewal.model.PasswordRenewalMark;

import java.util.Optional;

public interface PasswordRenewalMarkService {
    Optional<PasswordRenewalMark> findPasswordRenewalMark(String username);
}
