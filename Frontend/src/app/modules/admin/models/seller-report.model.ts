
export class SellerReport {
    id: number;
    sellerEmail: string;
    buyerEmail: string;
    buyerArrived: boolean;
    shouldGetPenalty: boolean;
    comment: string;

    constructor(
        id: number,
        sellerEmail: string,
        buyerEmail: string,
        buyerArrived: boolean,
        shouldGetPenalty: boolean,
        comment: string
    ) {
        this.id = id
        this.sellerEmail = sellerEmail
        this.buyerEmail = buyerEmail
        this.buyerArrived = buyerArrived
        this.shouldGetPenalty = shouldGetPenalty
        this.comment = comment
    }


}