export class GlobalConfig{
    public id: number;
    public systemFee: number;
    public buyerPointsPerReservation: number;
    public sellerPointsPerReservation: number;

    public constructor(id: number, systemFee: number, buyerPointsPerReservation: number, sellerPointsPerReservation: number){
        this.id = id;
        this.systemFee = systemFee;
        this.buyerPointsPerReservation = buyerPointsPerReservation;
        this.sellerPointsPerReservation = sellerPointsPerReservation;
    }
}