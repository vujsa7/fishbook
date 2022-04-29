export class EntityImage {
    priority: number;
    entityId: number;

    constructor(name: string, priority: number, entityId: number){
        this.priority = priority;
        this.entityId = entityId;
    }
}