export enum LoyaltyType {
    BABY_SEAHORSE,
    WILD_SWORDFISH,
    EXPERIENCED_SHARK,
    MIGHTY_WHALE
}

export class LoyaltyConfig{
    public id: number;
    public loyaltyType: LoyaltyType;
    public buyerMinPoints: number;
    public sellerMinPoints: number;
    public discount: number;
    public extraRevenue: number;

    public constructor(id: number, loyaltyType: LoyaltyType, buyerMinPoints: number, sellerMinPoints: number, discount: number, extraRevenue: number){
        this.id = id;
        this.loyaltyType =loyaltyType;
        this.buyerMinPoints = buyerMinPoints;
        this.sellerMinPoints = sellerMinPoints;
        this.discount = discount;
        this.extraRevenue = extraRevenue;
    }
}