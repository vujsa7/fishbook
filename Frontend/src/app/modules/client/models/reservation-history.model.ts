export class ReservationHistory {

    id: number;
    imageUrl: string;
    name: string;
    type: string;
    started: string;
    ended: string;
    price: number;
    status: string;
    entityId: number;

    constructor(
        id: number,
        imageUrl: string,
        name: string,
        type: string,
        started: string,
        ended: string,
        price: number,
        status: string,
        entityId: number
    ) {
        this.id = id
        this.imageUrl = imageUrl
        this.name = name
        this.type = type
        this.started = started
        this.ended = ended
        this.price = price
        this.status = status
        this.entityId = entityId
    }
    
}