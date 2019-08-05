package com.koohpar.dstrbt.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.IDN;

/**
 * Created by cmos on 14/04/2018.
 */

public class BrandResponse {
    @SerializedName("ID")
    @Expose
    private String ID;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Picture")
    @Expose
    private Picture Picture;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public com.koohpar.dstrbt.data.model.api.Picture getPicture() {
        return Picture;
    }

    public void setPicture(com.koohpar.dstrbt.data.model.api.Picture picture) {
        Picture = picture;
    }
}
