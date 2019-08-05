package com.koohpar.dstrbt.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cmos on 25/06/2018.
 */
public class DetailReportListResponse {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("stuffBrandName")
    @Expose
    private String stuffBrandName;

    @SerializedName("Price")
    @Expose
    private String price;

    @SerializedName("count")
    @Expose
    private String count;

    @SerializedName("Offer")
    @Expose
    private String Offer;

    @SerializedName("ConsumerPrice")
    @Expose
    private String ConsumerPrice;

    @SerializedName("SumPrice")
    @Expose
    private String SumPrice;

    public String getOffer() {
        return Offer;
    }

    public void setOffer(String offer) {
        Offer = offer;
    }

    public String getConsumerPrice() {
        return ConsumerPrice;
    }

    public void setConsumerPrice(String consumerPrice) {
        ConsumerPrice = consumerPrice;
    }

    public String getSumPrice() {
        return SumPrice;
    }

    public void setSumPrice(String sumPrice) {
        SumPrice = sumPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStuffBrandName() {
        return stuffBrandName;
    }

    public void setStuffBrandName(String stuffBrandName) {
        this.stuffBrandName = stuffBrandName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
