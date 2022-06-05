package com.fishbook.house.service.impl;

import com.fishbook.additional.entity.information.dao.AdditionalServiceRepository;
import com.fishbook.entity.dao.EntityRepository;
import com.fishbook.exception.EntityReservedException;
import com.fishbook.house.dao.HouseRepository;
import com.fishbook.house.dao.RoomRepository;
import com.fishbook.house.model.House;
import com.fishbook.house.model.Room;
import com.fishbook.house.service.HouseService;
import com.fishbook.reservation.dao.SellerReservationRepository;
import com.fishbook.user.dao.UserRepository;
import com.fishbook.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {
    private final HouseRepository houseRepository;
    private final RoomRepository roomRepository;
    private final EntityRepository entityRepository;
    private final UserRepository userRepository;
    private final AdditionalServiceRepository additionalServiceRepository;
    private final SellerReservationRepository reservationRepository;

    @Override
    public Optional<House> findById(Long id) {
        return houseRepository.findById(id);
    }

    @Override
    public Long save(House newHouse) {
        additionalServiceRepository.saveAll(newHouse.getAdditionalServices());
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
    public List<House> getAllByOwnerUsername(String username) {
        User owner = userRepository.findByEmail(username);
        return houseRepository.findAllByOwner(owner);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteById(Long id) {
        if (reservationRepository.findActiveAndFutureReservations(id).size() > 0) {
            throw new EntityReservedException();
        }
        entityRepository.deleteById(id);
    }

}
