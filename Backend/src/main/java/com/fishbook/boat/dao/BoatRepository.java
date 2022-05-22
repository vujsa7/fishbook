package com.fishbook.boat.dao;


import com.fishbook.boat.model.Boat;
import com.fishbook.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoatRepository extends JpaRepository<Boat, Long> {
    List<Boat> findAllByOwner(User user);
}
