import { AdditionalService } from "../../../shared/models/additional-service.model";
import { Rule } from "../../../shared/models/rule.model";
import { Room } from "./room.model";

export class HouseRegistrationRequest{
    public name: string;
    public description: string;
    public address: string;
    public city: string;
    public maxPeople: number;
    public cancellationFee: number;
    public price: number;
    public appliedRules: Array<Rule> = new Array();
    public rooms: Array<Room> = new Array();
    public additionalServices: Array<AdditionalService> = new Array();

    public constructor(name: string, description: string, address: string, city: string, maxPeople: number, cancellationFee: number, price: number) {
            this.name = name;
            this.description = description;
            this.address = address;
            this.city = city;
            this.maxPeople = maxPeople;
            this.cancellationFee = cancellationFee;
            this.price = price;
    }
}