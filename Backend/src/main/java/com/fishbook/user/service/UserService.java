package com.fishbook.user.service;

import com.fishbook.registration.model.RegistrationRequest;
import com.fishbook.user.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(User user);
    User save(RegistrationRequest request);
    User findByEmail(String email);
}
