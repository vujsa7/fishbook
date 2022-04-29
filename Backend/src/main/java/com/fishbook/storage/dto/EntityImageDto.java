package com.fishbook.storage.dto;

public class EntityImageDto {
    private Integer priority;
    private Long entityId;

    public EntityImageDto() {
    }

    public EntityImageDto(Integer priority, Long entityId) {
        this.priority = priority;
        this.entityId = entityId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
}
