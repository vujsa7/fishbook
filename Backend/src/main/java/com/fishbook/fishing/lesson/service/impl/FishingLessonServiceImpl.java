package com.fishbook.fishing.lesson.service.impl;

import com.fishbook.fishing.lesson.dao.FishingLessonRepository;
import com.fishbook.fishing.lesson.model.FishingLesson;
import com.fishbook.fishing.lesson.service.FishingLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FishingLessonServiceImpl implements FishingLessonService {
    private final FishingLessonRepository fishingLessonRepository;

    @Autowired
    public FishingLessonServiceImpl(FishingLessonRepository fishingLessonRepository) {
        this.fishingLessonRepository = fishingLessonRepository;
    }

    @Override
    public FishingLesson save(FishingLesson fishingLesson) {
        return fishingLessonRepository.save(fishingLesson);
    }
}
