import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'loyaltyType'})
export class LoyaltyTypePipe implements PipeTransform {
  transform(value: number): string {
    if(value <= 500)
        return "Baby Seahorse"
    else if(value <= 1000)
        return "Wild Swordfish"
    else if(value <= 2000)
        return "Experienced Shark"
    else if(value > 2000)
        return "Mighty Whale"
    else return "Unknown Type"
  }
}