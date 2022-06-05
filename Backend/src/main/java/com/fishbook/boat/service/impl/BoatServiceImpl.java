package com.fishbook.boat.service.impl;

import com.fishbook.additional.entity.information.dao.AdditionalServiceRepository;
import com.fishbook.boat.dao.BoatRepository;
import com.fishbook.boat.model.Boat;
import com.fishbook.boat.service.BoatService;
import com.fishbook.entity.dao.EntityRepository;
import com.fishbook.exception.EntityReservedException;
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
public class BoatServiceImpl implements BoatService {
    private final BoatRepository boatRepository;
    private final AdditionalServiceRepository additionalServiceRepository;
    private final EntityRepository entityRepository;
    private final UserRepository userRepository;
    private final SellerReservationRepository reservationRepository;

    @Override
    public Optional<Boat> findById(Long id) {
        return boatRepository.findById(id);
    }

    @Override
    public Long save(Boat boat) {
        additionalServiceRepository.saveAll(boat.getAdditionalServices());
        return boatRepository.save(boat).getId();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteById(Long id) {
        if (reservationRepository.findActiveAndFutureReservations(id).size() > 0) {
            throw new EntityReservedException();
        }
        entityRepository.deleteById(id);
    }

    @Override
    public List<Boat> getAll() {
        return boatRepository.findAll();
    }

    @Override
    public List<Boat> getAllByOwnerUsername(String username) {
        User owner = userRepository.findByEmail(username);
        return boatRepository.findAllByOwner(owner);
    }

}
