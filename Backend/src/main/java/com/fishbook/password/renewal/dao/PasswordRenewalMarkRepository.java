package com.fishbook.password.renewal.dao;

import com.fishbook.password.renewal.model.PasswordRenewalMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordRenewalMarkRepository extends JpaRepository<PasswordRenewalMark, Long> {
    void deletePasswordRenewalMarkByUsername(String username);
    Optional<PasswordRenewalMark> findPasswordRenewalMarkByUsername(String username);
}
