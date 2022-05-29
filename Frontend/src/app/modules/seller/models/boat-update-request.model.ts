import { AdditionalService } from "../../../shared/models/additional-service.model";

export class BoatUpdateRequest{
    public id: number;
    public name: string;
    public description: string;
    public address: string;
    public city: string;
    public country: string;
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
    public rules: Array<string> = new Array();
    public fishingEquipment: Array<string> = new Array();
    public navigationEquipment: Array<string> = new Array();
    public additionalServices: Array<AdditionalService> = new Array();

    public constructor(id: number, name: string, description: string, address: string, city: string, country: string, length: number, motors: number, power: number, maxSpeed: number, maxPeople: number, loadCapacity: number, 
        fuelConsumption: number, maxDistance: number, energyConsumption: number, cancellationFee: number, price: number, boatType: string, rules: Array<string>, fishingEquipment: Array<string>, navigationEquipment: Array<string>) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.address = address;
            this.city = city;
            this.country = country;
            this.length = length;
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
            this.rules = rules;
            this.fishingEquipment = fishingEquipment;
            this.navigationEquipment = navigationEquipment;
    }
}