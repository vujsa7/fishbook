package com.fishbook.house.service.impl;

import com.fishbook.house.dao.HouseRepository;
import com.fishbook.house.dao.RoomRepository;
import com.fishbook.house.model.House;
import com.fishbook.house.model.Room;
import com.fishbook.house.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HouseServiceImpl implements HouseService {
    private final HouseRepository houseRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public HouseServiceImpl(HouseRepository houseRepository, RoomRepository roomRepository) {
        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
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

}
