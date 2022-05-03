package com.fishbook.storage.model;

import javax.persistence.*;
import com.fishbook.entity.model.Entity;

@javax.persistence.Entity
public class EntityImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private Integer priority;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "entity_id")
    private Entity entity;

    public EntityImage() {
    }

    public EntityImage(String name, Integer priority) {
        this.name = name;
        this.priority = priority;
    }

    public EntityImage(String name, Integer priority, Entity entity) {
        this.name = name;
        this.priority = priority;
        this.entity = entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
