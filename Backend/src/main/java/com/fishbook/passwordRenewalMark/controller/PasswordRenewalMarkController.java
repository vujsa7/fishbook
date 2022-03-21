package com.fishbook.passwordRenewalMark.controller;

import com.fishbook.passwordRenewalMark.dto.RenewedPassword;
import com.fishbook.passwordRenewalMark.model.PasswordRenewalMark;
import com.fishbook.passwordRenewalMark.service.PasswordRenewalMarkService;
import com.fishbook.user.model.User;
import com.fishbook.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/passwordRenewalMarks", consumes = "application/json", produces = "application/json")
public class PasswordRenewalMarkController {
    private final PasswordRenewalMarkService passwordRenewalMarkService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordRenewalMarkController(PasswordRenewalMarkService passwordRenewalMarkService, UserService userService, PasswordEncoder passwordEncoder){
        this.passwordRenewalMarkService = passwordRenewalMarkService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity getPasswordRenewalMark(@RequestParam("username") String username){
        Optional<PasswordRenewalMark> passwordRenewalMark = passwordRenewalMarkService.findPasswordRenewalMark(username);
        if(passwordRenewalMark.isPresent()){
            return new ResponseEntity<>(passwordRenewalMark, HttpStatus.OK);
        }
        return new ResponseEntity<>("User is not marked for password renewal", HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity updatePasswordRenewalMark(@RequestBody RenewedPassword renewedPassword, @PathVariable String email){
        User user = userService.findByEmail(email);
        if(user == null){
            return new ResponseEntity<>("User doesn't exist.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        user.setPassword(passwordEncoder.encode(renewedPassword.getPassword()));
        userService.save(user);
        passwordRenewalMarkService.unmarkUserForPasswordRenewal(email);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
