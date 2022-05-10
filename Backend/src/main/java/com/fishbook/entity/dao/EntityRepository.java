package com.fishbook.entity.dao;

import com.fishbook.entity.model.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EntityRepository extends JpaRepository<Entity, Long> {
    @Modifying
    @Query("update Entity e set e.isDeleted = true where e.id = :id")
    void deleteById(Long id);
    @Modifying
    @Query("update Entity e set e.isDeleted = true where e.owner.id = :ownerId")
    void deleteByOwner(Long ownerId);
}
