import { AdditionalService } from "../../../shared/models/additional-service.model";
import { Rule } from "../../../shared/models/rule.model";
import { Equipment } from "../../../shared/models/equipment.model";
import { Address } from "src/app/models/location/address.model";

export class AdventureRegistrationRequest{
    public name: string;
    public description: string;
    public address: Address;
    public maxNumberOfPeople: number;
    public cancellationFee: number;
    public pricePerDay: number;
    public rules: Array<Rule> = new Array();
    public equipment: Array<Equipment> = new Array();
    public additionalServices: Array<AdditionalService> = new Array();
    public instructorBiography: string;

    constructor(name: string, description: string, address: Address, maxNumberOfPeople: number, cancellationFee: number, pricePerDay: number, rules: Array<Rule>, equipment: Array<Equipment>, additionalServices: Array<AdditionalService>, instructorBiography: string) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.maxNumberOfPeople = maxNumberOfPeople;
        this.cancellationFee = cancellationFee;
        this.pricePerDay = pricePerDay;
        this.rules = rules;
        this.equipment = equipment;
        this.additionalServices = additionalServices;
        this.instructorBiography = instructorBiography;
    }
}