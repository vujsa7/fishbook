import { AdditionalService } from "src/app/shared/models/additional-service.model";
import { MapLocation } from "src/app/shared/models/map-location.model";
import { SpecialOffer } from "./special-offer.model";

export class AdventureDetails {
    id: number;
    images: Array<string>;
    name: string;
    seller: string;
    description: string;
    rating: number;
    price: number;
    cancellationFee: number;
    location: MapLocation;
    maxPeople: number;
    sellerImg: string;
    aboutSeller: string;
    specialOffers: Array<SpecialOffer>;
    rules: Array<string>;
    fishingEquipment: Array<string>;
    previousAdventureImages: Array<string>;
    additionalServices: Array<AdditionalService>;

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
        maxPeople: number,
        sellerImg: string,
        aboutSeller: string,
        specialOffers: Array<SpecialOffer>,
        rules: Array<string>,
        fishingEquipment: Array<string>,
        previousAdventureImages: Array<string>,
        additionalServices: Array<AdditionalService>
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
        this.maxPeople = maxPeople
        this.sellerImg = sellerImg
        this.aboutSeller = aboutSeller
        this.specialOffers = specialOffers
        this.rules = rules
        this.fishingEquipment = fishingEquipment
        this.previousAdventureImages = previousAdventureImages
        this.additionalServices = additionalServices
    }

}