import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'registrationType'
})
export class RegistrationTypePipe implements PipeTransform {

  transform(value: string): string {
    if(value == 'ROLE_BOAT_OWNER'){
      return 'Boat owner';
    }
    if(value == 'ROLE_INSTRUCTOR'){
      return 'Instructor';
    }
    if(value == 'ROLE_HOUSE_OWNER'){
      return 'House owner';
    }
    return '';
  }

}
