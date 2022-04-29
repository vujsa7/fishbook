package com.fishbook.additional.entity.information.dao;

import com.fishbook.additional.entity.information.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findAllByType(String type);
}
