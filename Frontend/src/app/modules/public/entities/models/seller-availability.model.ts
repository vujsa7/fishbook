import { DateRange } from "src/app/shared/models/date-range.model";

export class SellerAvailability {
    email: string;
    availability: Array<DateRange>;

    constructor(email: string, availability: Array<DateRange>) {
        this.email = email
        this.availability = availability
    }

}