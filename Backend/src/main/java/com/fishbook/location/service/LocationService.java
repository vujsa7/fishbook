package com.fishbook.location.service;

import com.fishbook.location.model.City;
import com.fishbook.location.model.Country;

import java.util.List;

public interface LocationService {
    List<Country> getAllCountries();
    List<City> getAllCities();
}
