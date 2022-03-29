package com.fishbook.user.controller;

import com.fishbook.email.model.Email;
import com.fishbook.email.service.EmailService;
import com.fishbook.registration.model.VerificationCode;
import com.fishbook.registration.service.VerificationCodeService;
import com.fishbook.user.dto.UserRegistrationDto;
import com.fishbook.user.model.User;
import com.fishbook.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeService verficationCodeService;


    @Autowired
    public UserController(UserService userService, EmailService emailService, PasswordEncoder passwordEncoder, VerificationCodeService verificationCodeService) {
        this.userService = userService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.verficationCodeService = verificationCodeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerNewClient(@RequestBody UserRegistrationDto userRegistrationDto, HttpServletRequest request){
        if(userService.findByEmail(userRegistrationDto.getEmail()) != null){
            return new ResponseEntity<>(userRegistrationDto, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        // TODO: Add validation, exception throwing and geocoding
        try{
            userRegistrationDto.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
            String verificationCode = userService.saveNewClient(userRegistrationDto);

            //TODO: Move into emailService
            sendVerificationCode(userRegistrationDto.getEmail(), verificationCode, request.getRequestURI());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/verificationCodes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity verifyEmailAddress(@RequestParam String code){

        // TODO: Move into service method this logic
        VerificationCode verificationCode = verficationCodeService.findByVerificationCode(code);
        if(verificationCode == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try{
            userService.activateUser(verificationCode);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("http://localhost:4200/"));
            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private void sendVerificationCode(String emailAddress, String verificationCode, String url) throws MailException, InterruptedException{
        Email email = new Email(emailAddress, "Complete registration for Fishbook",
                "Follow the activation link to activate your account:\n" +
                        "http://localhost:8080" + url + "/verificationCodes?code=" + verificationCode);
        emailService.sendEmail(email);
    }

}
