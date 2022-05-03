export class EntityImage {
    priority: number;
    entityId: number;

    constructor(priority: number, entityId: number){
        this.priority = priority;
        this.entityId = entityId;
    }
}