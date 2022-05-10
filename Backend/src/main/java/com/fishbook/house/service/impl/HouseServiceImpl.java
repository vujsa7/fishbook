package com.fishbook.house.service.impl;

import com.fishbook.entity.dao.EntityRepository;
import com.fishbook.house.dao.HouseRepository;
import com.fishbook.house.dao.RoomRepository;
import com.fishbook.house.model.House;
import com.fishbook.house.model.Room;
import com.fishbook.house.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class HouseServiceImpl implements HouseService {
    private final HouseRepository houseRepository;
    private final RoomRepository roomRepository;
    private final EntityRepository entityRepository;

    @Autowired
    public HouseServiceImpl(HouseRepository houseRepository, RoomRepository roomRepository, EntityRepository entityRepository) {
        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
        this.entityRepository = entityRepository;
    }

    @Override
    public Optional<House> findById(Long id) {
        return houseRepository.findById(id);
    }

    @Override
    public Long saveNewHouse(House newHouse) {
        House house = houseRepository.save(newHouse);
        for(Room room : house.getRooms()){
            room.setHouse(house);
            roomRepository.save(room);
        }
        return house.getId();
    }

    @Override
    public List<House> getAll() {
        return houseRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        entityRepository.deleteById(id);
    }

}
