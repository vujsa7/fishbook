package com.fishbook.storage.dto;

public class EntityImageDto {
    private Integer priority;
    private String type;
    private Long entityId;

    public EntityImageDto() {
    }

    public EntityImageDto(Integer priority, String type, Long entityId) {
        this.priority = priority;
        this.entityId = entityId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
}
