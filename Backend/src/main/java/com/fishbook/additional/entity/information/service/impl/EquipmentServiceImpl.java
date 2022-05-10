package com.fishbook.additional.entity.information.service.impl;

import com.fishbook.additional.entity.information.dao.EquipmentRepository;
import com.fishbook.additional.entity.information.model.Equipment;
import com.fishbook.additional.entity.information.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {
    private final EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentServiceImpl(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public List<Equipment> getBoatEquipment() {
        List<Equipment> boatEquipment = equipmentRepository.findAllByType("navigation");
        List<Equipment> fishingEquipment = equipmentRepository.findAllByType("fishing");
        boatEquipment.addAll(fishingEquipment);
        return boatEquipment;
    }

    @Override
    public List<Equipment> getFishingEquipment() {
        return equipmentRepository.findAllByType("fishing");
    }
}
