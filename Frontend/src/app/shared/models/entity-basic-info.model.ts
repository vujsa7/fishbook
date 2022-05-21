export class EntityBasicInfo {
  public id: number;
  public imageUrl: string;
  public name: string;
  public seller: string;
  public description: string;
  public city: string;
  public country: string;
  public rating: number;
  public price: number;


  constructor(
    id: number,
    imageUrl: string,
    name: string,
    seller: string,
    description: string,
    city: string,
    country: string,
    rating: number,
    price: number
  ) {
    this.id = id
    this.imageUrl = imageUrl
    this.name = name
    this.seller = seller
    this.description = description
    this.city = city
    this.country = country
    this.rating = rating
    this.price = price
  }

}