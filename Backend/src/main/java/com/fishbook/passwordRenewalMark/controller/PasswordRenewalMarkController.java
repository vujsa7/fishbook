package com.fishbook.passwordRenewalMark.controller;

import com.fishbook.passwordRenewalMark.model.PasswordRenewalMark;
import com.fishbook.passwordRenewalMark.service.PasswordRenewalMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/passwordRenewalMarks")
public class PasswordRenewalMarkController {
    private final PasswordRenewalMarkService service;

    @Autowired
    public PasswordRenewalMarkController(PasswordRenewalMarkService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity getPasswordRenewalMark(@RequestParam("username") String username){
        Optional<PasswordRenewalMark> passwordRenewalMark = service.findPasswordRenewalMark(username);
        if(passwordRenewalMark.isPresent()){
            return new ResponseEntity<>(passwordRenewalMark, HttpStatus.OK);
        }
        return new ResponseEntity<>("User is not marked for password renewal", HttpStatus.NOT_FOUND);
    }
}
