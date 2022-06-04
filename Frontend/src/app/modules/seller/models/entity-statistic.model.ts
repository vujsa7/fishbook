export class EntityStatistic {

    id: number;
    name: string;
    rating: number;
    reservations: number;

    constructor(
        id: number,
        name: string,
        rating: number,
        reservations: number
    ) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.reservations = reservations;
    }
}