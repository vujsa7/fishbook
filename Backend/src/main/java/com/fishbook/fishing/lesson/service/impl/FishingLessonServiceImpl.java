package com.fishbook.fishing.lesson.service.impl;

import com.fishbook.additional.entity.information.dao.AdditionalServiceRepository;
import com.fishbook.entity.dao.EntityRepository;
import com.fishbook.fishing.lesson.dao.FishingLessonRepository;
import com.fishbook.fishing.lesson.model.FishingLesson;
import com.fishbook.fishing.lesson.service.FishingLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FishingLessonServiceImpl implements FishingLessonService {
    private final FishingLessonRepository fishingLessonRepository;
    private final AdditionalServiceRepository additionalServiceRepository;
    private final EntityRepository entityRepository;

    @Autowired
    public FishingLessonServiceImpl(FishingLessonRepository fishingLessonRepository, AdditionalServiceRepository additionalServiceRepository, EntityRepository entityRepository) {
        this.fishingLessonRepository = fishingLessonRepository;
        this.additionalServiceRepository = additionalServiceRepository;
        this.entityRepository = entityRepository;
    }

    @Override
    public Optional<FishingLesson> findById(Long id) {
        return fishingLessonRepository.findById(id);
    }

    @Override
    @Transactional
    public FishingLesson save(FishingLesson fishingLesson) {
        additionalServiceRepository.saveAll(fishingLesson.getAdditionalServices());
        return fishingLessonRepository.save(fishingLesson);
    }

    @Override
    public List<FishingLesson> getAll() {
        return fishingLessonRepository.findAll();
    }

    @Override
    public List<FishingLesson> getAll(String ownerUsername) {
        return fishingLessonRepository.findAll(ownerUsername);
    }

    @Override
    public void deleteById(Long id) {
        entityRepository.deleteById(id);
    }
}
