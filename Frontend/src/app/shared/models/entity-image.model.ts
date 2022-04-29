export class EntityImage {
    priority: number;
    type: string;
    entityId: number;

    constructor(priority: number, type: string, entityId: number){
        this.priority = priority;
        this.type = type;
        this.entityId = entityId;
    }
}