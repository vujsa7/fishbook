package com.fishbook.fishing.lesson.dao;

import com.fishbook.fishing.lesson.model.FishingLesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FishingLessonRepository extends JpaRepository<FishingLesson, Long> {
}
