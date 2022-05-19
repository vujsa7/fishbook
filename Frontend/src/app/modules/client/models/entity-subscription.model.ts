export class EntitySubscription {
    id: number;
    imageUrl: string;
    type: string;
    activeSpecialOffers: number;

    constructor(
        id: number,
        imageUrl: string,
        type: string,
        activeSpecialOffers: number
    ) {
        this.id = id
        this.imageUrl = imageUrl
        this.type = type
        this.activeSpecialOffers = activeSpecialOffers
    }

}