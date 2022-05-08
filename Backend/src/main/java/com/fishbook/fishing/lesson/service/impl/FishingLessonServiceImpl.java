package com.fishbook.fishing.lesson.service.impl;

import com.fishbook.additional.entity.information.dao.AdditionalServiceRepository;
import com.fishbook.fishing.lesson.dao.FishingLessonRepository;
import com.fishbook.fishing.lesson.model.FishingLesson;
import com.fishbook.fishing.lesson.service.FishingLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FishingLessonServiceImpl implements FishingLessonService {
    private final FishingLessonRepository fishingLessonRepository;
    private final AdditionalServiceRepository additionalServiceRepository;

    @Autowired
    public FishingLessonServiceImpl(FishingLessonRepository fishingLessonRepository, AdditionalServiceRepository additionalServiceRepository) {
        this.fishingLessonRepository = fishingLessonRepository;
        this.additionalServiceRepository = additionalServiceRepository;
    }

    @Override
    @Transactional
    public FishingLesson save(FishingLesson fishingLesson) {
        additionalServiceRepository.saveAll(fishingLesson.getAdditionalServices());
        return fishingLessonRepository.save(fishingLesson);
    }
}
