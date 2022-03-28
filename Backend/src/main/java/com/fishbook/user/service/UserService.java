package com.fishbook.user.service;

import com.fishbook.registration.model.RegistrationRequest;
import com.fishbook.registration.model.VerificationCode;
import com.fishbook.user.dto.UserRegistrationDto;
import com.fishbook.user.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    String saveNewClient(UserRegistrationDto userRegistrationDto);
    User save(RegistrationRequest request);
    User findByEmail(String email);
    void activateUser(VerificationCode verificationCode);
    void updateAdminsPassword(User user);
    List<User> getUsers(String role);
}
