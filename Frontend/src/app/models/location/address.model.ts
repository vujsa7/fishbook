import { City } from "./city.model";

export class Address{
    public address: string;
    public city: City;

    constructor(address: string, city: City){
        this.address = address;
        this.city = city;
    }
}