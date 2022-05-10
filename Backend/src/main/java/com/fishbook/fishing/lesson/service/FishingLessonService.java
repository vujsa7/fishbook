package com.fishbook.fishing.lesson.service;

import com.fishbook.fishing.lesson.model.FishingLesson;

import java.util.List;
import java.util.Optional;

public interface FishingLessonService {
    Optional<FishingLesson> findById(Long id);
    FishingLesson save(FishingLesson fishingLesson);
    List<FishingLesson> getAll();
    void deleteById(Long id);
}
