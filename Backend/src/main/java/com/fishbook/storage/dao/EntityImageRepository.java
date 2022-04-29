package com.fishbook.storage.dao;

import com.fishbook.storage.model.EntityImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityImageRepository extends JpaRepository<EntityImage, Long> {
}
