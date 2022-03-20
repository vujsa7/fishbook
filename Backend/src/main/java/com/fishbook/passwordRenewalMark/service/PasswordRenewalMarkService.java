package com.fishbook.passwordRenewalMark.service;

public interface PasswordRenewalMarkService {
    void markUserForPasswordRenewal(String username);
    void unmarkUserForPasswordRenewal(String username);
}
