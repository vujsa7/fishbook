export class MapLocation {
    address: string;
    city: string;
    country: String;
    lon: number;
    lat: number;

    constructor(
        address: string,
        city: string,
        country: String,
        lon: number,
        lat: number
    ) {
        this.address = address
        this.city = city
        this.country = country
        this.lon = lon
        this.lat = lat
    }
    
}