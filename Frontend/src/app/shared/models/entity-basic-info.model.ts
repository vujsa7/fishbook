export class EntityBasicInfo {
    public id: number;
    public imageUrl: string;
    public name: string;
    public seller: string;
    public description: string;
    public location: string;
    public rating: number;
    public price: number;

    constructor(
        id: number,
        imageUrl: string,
        name: string,
        seller: string,
        description: string,
        location: string,
        rating: number,
        price: number
    ) {
        this.id = id
        this.imageUrl = imageUrl
        this.name = name
        this.seller = seller
        this.description = description
        this.location = location
        this.rating = rating
        this.price = price
    }
}