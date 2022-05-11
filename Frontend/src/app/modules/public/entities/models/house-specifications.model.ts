export class HouseSpecifications {
  roomCount: number;
  bedsByRooms: string;
  totalBeds: number;

  constructor(roomCount: number, bedsByRooms: string, totalBeds: number) {
    this.roomCount = roomCount
    this.bedsByRooms = bedsByRooms
    this.totalBeds = totalBeds
  }
}