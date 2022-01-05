package com.fishbook.user.service.impl;

import com.fishbook.location.dao.AddressRepository;
import com.fishbook.location.dao.CityRepository;
import com.fishbook.location.dao.CountryRepository;
import com.fishbook.location.model.Address;
import com.fishbook.location.model.City;
import com.fishbook.location.model.Country;
import com.fishbook.registration.model.RegistrationRequest;
import com.fishbook.user.dao.RoleRepository;
import com.fishbook.user.dao.UserRepository;
import com.fishbook.user.model.Role;
import com.fishbook.user.model.User;
import com.fishbook.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, CountryRepository countryRepository,
                           CityRepository cityRepository, AddressRepository addressRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.addressRepository = addressRepository;
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
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User save(RegistrationRequest request) {
//        Country country = countryRepository.findByName(request.getCountry());
//        if(country == null){
//            country = new Country(request.getCountry().substring(0, 2), request.getCountry());
//            countryRepository.save(country);
//        }
//        City city = cityRepository.findByName(request.getCity());
//        if(city == null){
//            city = new City("postalCode", request.getCity(), country);
//            cityRepository.save(city);
//        }
//        Address address = new Address(request.getAddress(), city);
//        addressRepository.save(address);
//        Role role = roleRepository.findByName(request.getRegistrationType());
//
//        User user = new User(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), request.getPhoneNumber(), address, role);
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
