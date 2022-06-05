import { DateRange } from "src/app/shared/models/date-range.model";

export class EntityAvailability {
    id: number;
    availability: Array<DateRange>;

    constructor(id: number, availability: Array<DateRange>) {
        this.id = id
        this.availability = availability
    }

}