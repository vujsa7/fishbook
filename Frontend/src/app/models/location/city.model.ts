import { Country } from "./country.model";

export class City{
    public id: number;
    public name: string;
    public postalCode: string;
    public country: Country;

    constructor(){
        this.id = -1;
        this.name = "";
        this.postalCode = "";
        this.country = new Country();
    }
}