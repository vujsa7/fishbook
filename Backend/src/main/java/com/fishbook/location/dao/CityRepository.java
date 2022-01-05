package com.fishbook.location.dao;

import com.fishbook.location.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

}
