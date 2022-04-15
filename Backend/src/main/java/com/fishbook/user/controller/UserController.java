package com.fishbook.user.controller;

import com.fishbook.email.model.Email;
import com.fishbook.email.service.EmailService;
import com.fishbook.password.renewal.dto.RenewedPassword;
import com.fishbook.registration.model.VerificationCode;
import com.fishbook.registration.service.VerificationCodeService;
import com.fishbook.user.dto.PasswordUpdateDto;
import com.fishbook.user.dto.UserDto;
import com.fishbook.user.dto.UserInfoDto;
import com.fishbook.user.dto.UserRegistrationDto;
import com.fishbook.user.model.User;
import com.fishbook.user.service.RoleService;
import com.fishbook.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeService verificationCodeService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, EmailService emailService, PasswordEncoder passwordEncoder, VerificationCodeService verificationCodeService, RoleService roleService) {
        this.userService = userService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.verificationCodeService = verificationCodeService;
        this.roleService = roleService;
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

    @RequestMapping(value = "/verificationCodes/{code}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity verifyEmailAddress(@PathVariable() String code){

        // TODO: Move into service method this logic
        VerificationCode verificationCode = verificationCodeService.findByVerificationCode(code);
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
                        "http://localhost:8080" + url + "/verificationCodes/" + verificationCode);
        emailService.sendEmail(email);
    }

    @PostMapping(value = "/admins", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity createAdmin(@RequestBody UserDto userDto){
        if(userService.findByEmail(userDto.getEmail()) != null){
            return new ResponseEntity<>(userDto, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User admin = new User(userDto.getFirstName(), userDto.getLastName(),
                userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), userDto.getPhoneNumber(),
                userDto.getAddress(), roleService.findByName("ROLE_ADMIN"));

        userService.saveAdmin(admin);
        return new ResponseEntity<>(admin, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity getUsers(@RequestParam(value = "role") String role){
        List<UserInfoDto> users = userService.getUsers(role).stream()
                .map(user -> new UserInfoDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(), user.getAddress().getCity().getCountry().getName(),
                        user.getAddress().getCity().getName(), user.getAddress().getAddress(), user.getDeleted(), user.isEnabled()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteUser(@PathVariable() String username){
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'BOAT_OWNER', 'HOUSE_OWNER')")
    public ResponseEntity<UserInfoDto> getUser(@PathVariable String username, Principal principal){
        if(!Objects.equals(username, principal.getName())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userService.findByEmail(username);
        UserInfoDto userDto = new UserInfoDto(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(), user.getAddress().getCity().getCountry().getName(),
                user.getAddress().getCity().getName(), user.getAddress().getAddress());

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping(value = "/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'BOAT_OWNER', 'HOUSE_OWNER')")
    public ResponseEntity updateUser(@PathVariable String username, Principal principal, @RequestBody UserDto userDto){
        if(!Objects.equals(username, principal.getName())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userService.findByEmail(username);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAddress(userDto.getAddress());
        user.setPhoneNumber(user.getPhoneNumber());
        userService.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{username}/password")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'BOAT_OWNER', 'HOUSE_OWNER')")
    public ResponseEntity updatePassword(@PathVariable String username, Principal principal, @RequestBody PasswordUpdateDto passwordUpdateDto){
        if(!Objects.equals(username, principal.getName())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userService.findByEmail(username);
        if(!passwordEncoder.matches(passwordUpdateDto.getCurrentPassword(), user.getPassword())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(passwordUpdateDto.getNewPassword()));
        userService.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
