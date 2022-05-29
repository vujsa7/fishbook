package com.fishbook.house.service.impl;

import com.fishbook.additional.entity.information.dao.AdditionalServiceRepository;
import com.fishbook.entity.dao.EntityRepository;
import com.fishbook.house.dao.HouseRepository;
import com.fishbook.house.dao.RoomRepository;
import com.fishbook.house.model.House;
import com.fishbook.house.model.Room;
import com.fishbook.house.service.HouseService;
import com.fishbook.user.dao.UserRepository;
import com.fishbook.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class HouseServiceImpl implements HouseService {
    private final HouseRepository houseRepository;
    private final RoomRepository roomRepository;
    private final EntityRepository entityRepository;
    private final UserRepository userRepository;
    private final AdditionalServiceRepository additionalServiceRepository;

    @Autowired
    public HouseServiceImpl(HouseRepository houseRepository, RoomRepository roomRepository, EntityRepository entityRepository, UserRepository userRepository, AdditionalServiceRepository additionalServiceRepository) {
        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
        this.entityRepository = entityRepository;
        this.userRepository = userRepository;
        this.additionalServiceRepository = additionalServiceRepository;
    }

    @Override
    public Optional<House> findById(Long id) {
        return houseRepository.findById(id);
    }

    @Override
    public Long save(House house) {
        additionalServiceRepository.saveAll(house.getAdditionalServices());
        roomRepository.saveAll(house.getRooms());
        return houseRepository.save(house).getId();
    }

    @Override
    public List<House> getAll() {
        return houseRepository.findAll();
    }

    @Override
    public List<House> getAllByOwnerUsername(String username) {
        User owner = userRepository.findByEmail(username);
        return houseRepository.findAllByOwner(owner);
    }

    @Override
    public void deleteById(Long id) {
        entityRepository.deleteById(id);
    }

}
