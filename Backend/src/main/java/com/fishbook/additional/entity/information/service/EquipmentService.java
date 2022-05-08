package com.fishbook.additional.entity.information.service;

import com.fishbook.additional.entity.information.model.Equipment;

import java.util.List;

public interface EquipmentService {
    List<Equipment> getBoatEquipment();
    List<Equipment> getFishingEquipment();
}
