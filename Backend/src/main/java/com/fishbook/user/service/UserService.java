package com.fishbook.user.service;

import com.fishbook.registration.model.RegistrationRequest;
import com.fishbook.registration.model.VerificationCode;
import com.fishbook.user.dto.UserRegistrationDto;
import com.fishbook.user.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    String saveNewClient(UserRegistrationDto userRegistrationDto);
    void save(RegistrationRequest request);
    void save(User user);
    User findByEmail(String email);
    void activateUser(VerificationCode verificationCode);
    void saveAdmin(User user);
    void updateAdminsPassword(User user);
    List<User> getUsers(String role);
    void deleteUser(Long id);
    void createDeleteAccountRequest(String email, String requestMessage);
}
