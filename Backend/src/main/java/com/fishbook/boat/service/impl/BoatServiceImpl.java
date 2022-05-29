package com.fishbook.boat.service.impl;

import com.fishbook.additional.entity.information.dao.AdditionalServiceRepository;
import com.fishbook.boat.dao.BoatRepository;
import com.fishbook.boat.model.Boat;
import com.fishbook.boat.service.BoatService;
import com.fishbook.entity.dao.EntityRepository;
import com.fishbook.user.dao.UserRepository;
import com.fishbook.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoatServiceImpl implements BoatService {
    private final BoatRepository boatRepository;
    private final AdditionalServiceRepository additionalServiceRepository;
    private final EntityRepository entityRepository;
    private final UserRepository userRepository;

    @Autowired
    public BoatServiceImpl(BoatRepository boatRepository, AdditionalServiceRepository additionalServiceRepository, EntityRepository entityRepository, UserRepository userRepository) {
        this.boatRepository = boatRepository;
        this.additionalServiceRepository = additionalServiceRepository;
        this.entityRepository = entityRepository;
        this.userRepository = userRepository;
    }

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
    public void deleteById(Long id) {
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
