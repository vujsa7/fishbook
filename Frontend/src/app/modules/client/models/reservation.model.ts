export class Reservation {

    id: number;
    imageUrl: string;
    name: string;
    type: string;
    start: string;
    end: string;
    price: number;
    status: string;
    entityId: number;

    constructor(
        id: number,
        imageUrl: string,
        name: string,
        type: string,
        start: string,
        end: string,
        price: number,
        status: string,
        entityId: number
    ) {
        this.id = id
        this.imageUrl = imageUrl
        this.name = name
        this.type = type
        this.start = start
        this.end = end
        this.price = price
        this.status = status
        this.entityId = entityId
    }
    
}