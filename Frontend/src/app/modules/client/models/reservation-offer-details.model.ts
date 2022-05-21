import { AdditionalService } from "src/app/shared/models/additional-service.model";
import { DateRange } from "src/app/shared/models/date-range.model";

export class ReservationOfferDetails {

    entityId: number;
    entityName: string;
    pricePerDay: number;
    additionalServices: Array<AdditionalService>;
    availableDates: Array<DateRange>;

    constructor(
        entityId: number,
        entityName: string,
        pricePerDay: number,
        additionalServices: Array<AdditionalService>,
        availableDates: Array<DateRange>
    ) {
        this.entityId = entityId
        this.entityName = entityName
        this.pricePerDay = pricePerDay
        this.additionalServices = additionalServices
        this.availableDates = availableDates
    }

}