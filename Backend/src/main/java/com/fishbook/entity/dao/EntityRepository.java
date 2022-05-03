package com.fishbook.entity.dao;

import com.fishbook.entity.model.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityRepository extends JpaRepository<Entity, Long> {
}
