package com.fishbook.fishing.lesson.dto;

public class FishingLessonCreatedDto {
    private Long id;

    public FishingLessonCreatedDto() {
    }

    public FishingLessonCreatedDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
