export class EntitySubscription {
    id: number;
    imageUrl: string;
    type: string;
    activeSpecialOffers: number;
    entityId: number;

    constructor(
        id: number,
        imageUrl: string,
        type: string,
        activeSpecialOffers: number,
        entityId: number
    ) {
        this.id = id
        this.imageUrl = imageUrl
        this.type = type
        this.activeSpecialOffers = activeSpecialOffers
        this.entityId = entityId
    }

}