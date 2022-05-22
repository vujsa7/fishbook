package com.fishbook.house.dao;

import com.fishbook.house.model.House;
import com.fishbook.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findAllByOwner(User user);
}
