package com.fishbook.passwordRenewalMark.service;

import com.fishbook.passwordRenewalMark.model.PasswordRenewalMark;

import java.util.Optional;

public interface PasswordRenewalMarkService {
    void markUserForPasswordRenewal(String username);
    void unmarkUserForPasswordRenewal(String username);
    Optional<PasswordRenewalMark> findPasswordRenewalMark(String username);
}
