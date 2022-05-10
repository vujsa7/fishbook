package com.fishbook.boat.service.impl;

import com.fishbook.boat.dao.BoatRepository;
import com.fishbook.boat.model.Boat;
import com.fishbook.boat.service.BoatService;
import com.fishbook.entity.dao.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoatServiceImpl implements BoatService {
    private final BoatRepository boatRepository;
    private final EntityRepository entityRepository;

    @Autowired
    public BoatServiceImpl(BoatRepository boatRepository, EntityRepository entityRepository) {
        this.boatRepository = boatRepository;
        this.entityRepository = entityRepository;
    }

    @Override
    public Optional<Boat> findById(Long id) {
        return boatRepository.findById(id);
    }

    @Override
    public Long saveNewBoat(Boat boat) {
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

}
