package com.fishbook.system.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GlobalConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double systemFee;
    private Integer buyerPointsPerReservation;
    private Integer sellerPointsPerReservation;

    public GlobalConfig() {
    }

    public Integer getId() {
        return id;
    }

    public Double getSystemFee() {
        return systemFee;
    }

    public Integer getBuyerPointsPerReservation() {
        return buyerPointsPerReservation;
    }

    public Integer getSellerPointsPerReservation() {
        return sellerPointsPerReservation;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSystemFee(Double systemFee) {
        this.systemFee = systemFee;
    }

    public void setBuyerPointsPerReservation(Integer buyerPointsPerReservation) {
        this.buyerPointsPerReservation = buyerPointsPerReservation;
    }

    public void setSellerPointsPerReservation(Integer sellerPointsPerReservation) {
        this.sellerPointsPerReservation = sellerPointsPerReservation;
    }
}
