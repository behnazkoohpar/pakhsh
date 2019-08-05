package com.koohpar.dstrbt.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cmos on 14/04/2018.
 */

public class SpecialOfferResponse {
    @SerializedName("ID")
    @Expose
    private String ID;
    @SerializedName("BrandName")
    @Expose
    private String BrandName;
    @SerializedName("StuffName")
    @Expose
    private String StuffName;
    @SerializedName("Price")
    @Expose
    private String Price;
    @SerializedName("offer")
    @Expose
    private String offer;

    @SerializedName("UnitName")
    @Expose
    private String UnitName;
    @SerializedName("LastStock")
    @Expose
    private String LastStock;
    @SerializedName("MinimumStuck")
    @Expose
    private String MinimumStuck;
    @SerializedName("ConsumerPrice")
    @Expose
    private String ConsumerPrice;


    @SerializedName("Picture")
    @Expose
    private Picture Picture;
    private int Visibility = 0;

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public String getLastStock() {
        return LastStock;
    }

    public void setLastStock(String lastStock) {
        LastStock = lastStock;
    }

    public String getMinimumStuck() {
        return MinimumStuck;
    }

    public void setMinimumStuck(String minimumStuck) {
        MinimumStuck = minimumStuck;
    }

    public String getConsumerPrice() {
        return ConsumerPrice;
    }

    public void setConsumerPrice(String consumerPrice) {
        ConsumerPrice = consumerPrice;
    }

    public int getVisibility() {
        return Visibility;
    }

    public void setVisibility(int visibility) {
        Visibility = visibility;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public String getStuffName() {
        return StuffName;
    }

    public void setStuffName(String stuffName) {
        StuffName = stuffName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public com.koohpar.dstrbt.data.model.api.Picture getPicture() {
        return Picture;
    }

    public void setPicture(com.koohpar.dstrbt.data.model.api.Picture picture) {
        Picture = picture;
    }
}
