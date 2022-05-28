import { AdditionalService } from "src/app/shared/models/additional-service.model";
import { Room } from "./room.model";

export class HouseUpdateRequest{
    public id: number;
    public name: string;
    public description: string;
    public address: string;
    public city: string;
    public country: string;
    public maxPeople: number;
    public cancellationFee: number;
    public price: number;
    public rules: Array<string> = new Array();
    public rooms: Array<Room> = new Array();
    public additionalServices: Array<AdditionalService> = new Array();

    public constructor(id: number, name: string, description: string, address: string, city: string, country: string, maxPeople: number, cancellationFee: number, price: number, rules: Array<string>){
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.city = city;
        this.country = country;
        this.maxPeople = maxPeople;
        this.cancellationFee = cancellationFee;
        this.price = price;
        this.rules = rules;
    }
}