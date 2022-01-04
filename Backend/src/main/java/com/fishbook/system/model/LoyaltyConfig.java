package com.fishbook.system.model;

import com.fishbook.user.model.LoyaltyType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LoyaltyConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LoyaltyType loyaltyType;
    private Integer buyerMinPoints;
    private Integer sellerMinPoints;
    private Double discount;
    private Double extraRevenue;

    public LoyaltyConfig() {
    }

    public Integer getId() {
        return id;
    }

    public LoyaltyType getLoyaltyType() {
        return loyaltyType;
    }

    public Integer getBuyerMinPoints() {
        return buyerMinPoints;
    }

    public Integer getSellerMinPoints() {
        return sellerMinPoints;
    }

    public Double getDiscount() {
        return discount;
    }

    public Double getExtraRevenue() {
        return extraRevenue;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLoyaltyType(LoyaltyType loyaltyType) {
        this.loyaltyType = loyaltyType;
    }

    public void setBuyerMinPoints(Integer buyerMinPoints) {
        this.buyerMinPoints = buyerMinPoints;
    }

    public void setSellerMinPoints(Integer sellerMinPoints) {
        this.sellerMinPoints = sellerMinPoints;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public void setExtraRevenue(Double extraRevenue) {
        this.extraRevenue = extraRevenue;
    }
}
