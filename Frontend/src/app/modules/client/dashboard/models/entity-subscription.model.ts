export class EntitySubscription {
    imageUrl: string;
    type: string;
    activeSpecialOffers: number;
    entityId: number;

    constructor(
        imageUrl: string,
        type: string,
        activeSpecialOffers: number,
        entityId: number
    ) {
        this.imageUrl = imageUrl
        this.type = type
        this.activeSpecialOffers = activeSpecialOffers
        this.entityId = entityId
    }

}