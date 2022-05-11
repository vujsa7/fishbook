export class BoatSpecifications {
    boatType: string;
    maxPeople: number;
    length: number;
    loadCapacity: number;
    maximumSpeed:number;
    horsepower: number;
    motorsOnBoat:number;
    fuelConsumption:number;
    maxDistanceOnTank: number;
    energyConsumption: number;

  constructor(
    boatType: string, 
    maxPeople: number, 
    length: number, 
    loadCapacity: number, 
    maximumSpeed: number, 
    horsepower: number, 
    motorsOnBoat: number, 
    fuelConsumption: number, 
    maxDistanceOnTank: number, 
    energyConsumption: number
) {
    this.boatType = boatType
    this.maxPeople = maxPeople
    this.length = length
    this.loadCapacity = loadCapacity
    this.maximumSpeed = maximumSpeed
    this.horsepower = horsepower
    this.motorsOnBoat = motorsOnBoat
    this.fuelConsumption = fuelConsumption
    this.maxDistanceOnTank = maxDistanceOnTank
    this.energyConsumption = energyConsumption
  }

}