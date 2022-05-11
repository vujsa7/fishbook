export class SpecialOffer {
  id: number;
  start: string;
  end: string;
  oldPrice: number;
  newPrice: number;

  constructor(
    id: number,
    start: string,
    end: string,
    oldPrice: number,
    newPrice: number
  ) {
    this.id = id
    this.start = start
    this.end = end
    this.oldPrice = oldPrice
    this.newPrice = newPrice
  }

}