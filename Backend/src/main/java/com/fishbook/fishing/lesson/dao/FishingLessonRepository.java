package com.fishbook.fishing.lesson.dao;

import com.fishbook.fishing.lesson.model.FishingLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FishingLessonRepository extends JpaRepository<FishingLesson, Long> {


    @Query("select f from FishingLesson f where f.owner.email like :ownerUsername")
    List<FishingLesson> findAll(String ownerUsername);
}
