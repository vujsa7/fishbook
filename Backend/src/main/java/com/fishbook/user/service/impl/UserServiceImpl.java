package com.fishbook.user.service.impl;

import com.fishbook.location.dao.AddressRepository;
import com.fishbook.location.dao.CityRepository;
import com.fishbook.location.model.Address;
import com.fishbook.location.model.City;
import com.fishbook.location.service.LocationService;
import com.fishbook.registration.dao.VerificationCodeRepository;
import com.fishbook.registration.model.VerificationCode;
import com.fishbook.registration.model.RegistrationRequest;
import com.fishbook.user.dao.RoleRepository;
import com.fishbook.user.dao.UserRepository;
import com.fishbook.user.dto.UserRegistrationDto;
import com.fishbook.user.model.Role;
import com.fishbook.user.model.User;
import com.fishbook.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;
    private final VerificationCodeRepository verificationCodeRepository;

    private final LocationService locationService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           CityRepository cityRepository, AddressRepository addressRepository,
                           LocationService locationService, VerificationCodeRepository verificationCodeRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.cityRepository = cityRepository;
        this.addressRepository = addressRepository;
        this.verificationCodeRepository = verificationCodeRepository;

        this.locationService = locationService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);
        if(user == null){
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", s));
        } else {
            return user;
        }
    }

    @Override
    public String saveNewClient(UserRegistrationDto userRegistrationDto){
        // TODO: Add validation, exception throwing and geocoding
        City city = locationService.findCityByName(userRegistrationDto.getCity());

        Role role = roleRepository.findByName("ROLE_CLIENT");
        Address address = new Address(userRegistrationDto.getAddress(), city, 0.0, 0.0);
        User user = new User(userRegistrationDto.getFirstName(), userRegistrationDto.getLastName(),
                userRegistrationDto.getEmail(), userRegistrationDto.getPassword(), userRegistrationDto.getPhoneNumber(),
                address, role, false);
        String activationGUIDString = generateActivationGUID();
        user.setVerificationCodes(new HashSet<>(){{
            add(new VerificationCode(activationGUIDString, new Date()));
        }});
        userRepository.save(user);
        return activationGUIDString;
    }

    private String generateActivationGUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    @Override
    public User save(RegistrationRequest request) {
        City city = cityRepository.findByName(request.getCity());
        Address address = new Address(request.getAddress(), city, 0.0, 0.0);
        addressRepository.save(address);
        Role role = roleRepository.findByName(request.getRegistrationType());

        User user = new User(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), request.getPhoneNumber(), address, role, true);
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void activateUser(VerificationCode verificationCode) {
        User user = verificationCode.getUser();
        if(user.getEnabled() == false){
            user.setEnabled(true);
            user.removeVerificationCode(verificationCode);
            userRepository.save(user);
            verificationCodeRepository.delete(verificationCode);
        }


        //else
            // TODO: Throw UserAlreadyEnabledException
    }
}
