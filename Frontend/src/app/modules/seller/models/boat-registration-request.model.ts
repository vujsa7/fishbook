import { AdditionalService } from "../../../shared/models/additional-service.model";
import { Rule } from "../../../shared/models/rule.model";
import { Equipment } from "../../../shared/models/equipment.model";

export class BoatRegistrationRequest{
    public name: string;
    public description: string;
    public address: string;
    public city: string;
    public length: number;
    public motors: number;
    public power: number;
    public maxSpeed: number;
    public maxPeople: number;
    public loadCapacity: number;
    public fuelConsumption: number;
    public maxDistance: number;
    public energyConsumption: number;
    public cancellationFee: number;
    public price: number;
    public boatType: string;
    public appliedRules: Array<Rule> = new Array();
    public equipment: Array<Equipment> = new Array();
    public additionalServices: Array<AdditionalService> = new Array();

    public constructor(name: string, description: string, address: string, city: string, length: number, motors: number, power: number, maxSpeed: number, maxPeople: number, loadCapacity: number, 
        fuelConsumption: number, maxDistance: number, energyConsumption: number, cancellationFee: number, price: number, boatType: string) {
            this.name = name;
            this.description = description;
            this.address = address;
            this.city = city;
            this. length = length;
            this.motors = motors;
            this.power = power;
            this.maxSpeed = maxSpeed;
            this.maxPeople = maxPeople;
            this.loadCapacity = loadCapacity;
            this.fuelConsumption = fuelConsumption;
            this.maxDistance = maxDistance;
            this.energyConsumption = energyConsumption;
            this.cancellationFee = cancellationFee;
            this.price = price;
            this.boatType = boatType;
    }
}