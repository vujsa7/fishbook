package com.fishbook.user.controller;

import com.fishbook.location.model.Address;
import com.fishbook.location.model.City;
import com.fishbook.location.service.LocationService;
import com.fishbook.user.dto.UserRegistrationDto;
import com.fishbook.user.model.Role;
import com.fishbook.user.model.User;
import com.fishbook.user.service.RoleService;
import com.fishbook.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final LocationService locationService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, RoleService roleService, LocationService locationService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.locationService = locationService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody UserRegistrationDto userRegistrationDto){
        if(userService.findByEmail(userRegistrationDto.getEmail()) != null){
            return new ResponseEntity<>(userRegistrationDto, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // TODO: Add validation, exception throwing and geocoding

        City city = locationService.findCityByName(userRegistrationDto.getCity());
        userRegistrationDto.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        Role role = roleService.findByName("ROLE_CLIENT");
        Address address = new Address(userRegistrationDto.getAddress(), city, 0.0, 0.0);
        User user = new User(userRegistrationDto.getFirstName(), userRegistrationDto.getLastName(),
                userRegistrationDto.getEmail(), userRegistrationDto.getPassword(), userRegistrationDto.getPhoneNumber(),
                address, role);

        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
