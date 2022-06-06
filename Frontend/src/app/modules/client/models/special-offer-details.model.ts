import { AdditionalService } from "src/app/shared/models/additional-service.model";


export class SpecialOfferDetails {
    id: number;
    entityId: number;
    entityName: string;
    additionalServices: Array<AdditionalService>;
    start: string;
    end: string;
    price: number;


    constructor(
        id: number,
        entityId: number,
        entityName: string,
        additionalServices: Array<AdditionalService>,
        start: string,
        end: string,
        price: number
    ) {
        this.id = id
        this.entityId = entityId
        this.entityName = entityName
        this.additionalServices = additionalServices
        this.start = start
        this.end = end
        this.price = price
    }

}