package com.fishbook.passwordRenewalMark.dao;

import com.fishbook.passwordRenewalMark.model.PasswordRenewalMark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRenewalMarkRepository extends JpaRepository<PasswordRenewalMark, Long> {
    void deletePasswordRenewalMarkByUsername(String username);
}
