package com.fishbook.location.service.impl;

import com.fishbook.location.dao.CityRepository;
import com.fishbook.location.dao.CountryRepository;
import com.fishbook.location.model.City;
import com.fishbook.location.model.Country;
import com.fishbook.location.service.LocationService;
import com.fishbook.registration.dao.RegistrationRequestRepository;
import com.fishbook.registration.model.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    @Autowired
    public LocationServiceImpl(CountryRepository countryRepository, CityRepository cityRepository){
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<Country> getAllCountries(){
        return countryRepository.findAll();
    }

    @Override
    public List<City> getAllCities(){
        return cityRepository.findAll();
    }
}
