import { MapLocation } from "src/app/shared/models/map-location.model";
import { BoatSpecifications } from "./boat-specifications.model";
import { SpecialOffer } from "./special-offer.model";

export class BoatDetails {
    id: number;
    images: Array<string>;
    name: string;
    seller: string;
    description: string;
    rating: number;
    price: number;
    cancellationFee: number;
    location: MapLocation;
    boatSpecifications: BoatSpecifications;
    specialOffers: Array<SpecialOffer>;
    rules: Array<string>;
    navigationEquipment: Array<string>;
    fishingEquipment: Array<string>;

    constructor(
        id: number,
        images: Array<string>,
        name: string,
        seller: string,
        description: string,
        rating: number,
        price: number,
        cancellationFee: number,
        location: MapLocation,
        boatSpecifications: BoatSpecifications,
        specialOffers: Array<SpecialOffer>,
        rules: Array<string>,
        navigationEquipment: Array<string>,
        fishingEquipment: Array<string>
    ) {
        this.id = id
        this.images = images
        this.name = name
        this.seller = seller
        this.description = description
        this.rating = rating
        this.price = price
        this.cancellationFee = cancellationFee
        this.location = location
        this.boatSpecifications = boatSpecifications
        this.specialOffers = specialOffers
        this.rules = rules
        this.navigationEquipment = navigationEquipment
        this.fishingEquipment = fishingEquipment
    }
}