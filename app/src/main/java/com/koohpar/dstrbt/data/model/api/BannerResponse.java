package com.koohpar.dstrbt.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cmos on 10/04/2018.
 */

public class BannerResponse {

    @SerializedName("Picture")
    @Expose
    private Picture Picture;

    @SerializedName("StuffID")
    @Expose
    private String StuffID;

    public com.koohpar.dstrbt.data.model.api.Picture getPicture() {
        return Picture;
    }

    public void setPicture(com.koohpar.dstrbt.data.model.api.Picture picture) {
        Picture = picture;
    }

    public String getStuffID() {
        return StuffID;
    }

    public void setStuffID(String stuffID) {
        StuffID = stuffID;
    }
}
