import { AdditionalService } from "../../../shared/models/additional-service.model";
import { Rule } from "../../../shared/models/rule.model";
import { Equipment } from "../../../shared/models/equipment.model";
import { Address } from "src/app/models/location/address.model";

export class AdventureUpdateRequest{
    public id: number;
    public name: string;
    public description: string;
    public address: string;
    public city: string;
    public country: string;
    public maxNumberOfPeople: number;
    public cancellationFee: number;
    public pricePerDay: number;
    public rules: Array<string> = new Array();
    public equipment: Array<string> = new Array();
    public instructorBiography: string;

    constructor(id: number, name: string, description: string, address: string, city: string, country: string, maxNumberOfPeople: number, cancellationFee: number, pricePerDay: number, rules: Array<string>, equipment: Array<string>, instructorBiography: string) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.city = city;
        this.country = country;
        this.maxNumberOfPeople = maxNumberOfPeople;
        this.cancellationFee = cancellationFee;
        this.pricePerDay = pricePerDay;
        this.rules = rules;
        this.equipment = equipment;
        this.instructorBiography = instructorBiography;
    }
}