package com.koohpar.dstrbt.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cmos on 11/04/2018.
 */

public class ProfileUserResponse {

    @SerializedName("UserID")
    @Expose
    private String UserID;
    @SerializedName("FirstName")
    @Expose
    private String FirstName;
    @SerializedName("LastName")
    @Expose
    private String LastName;
    @SerializedName("StoreName")
    @Expose
    private String StoreName;
    @SerializedName("PhoneNumber")
    @Expose
    private String PhoneNumber;
    @SerializedName("Address")
    @Expose
    private String Address;
    @SerializedName("Latitude")
    @Expose
    private String Latitude;
    @SerializedName("Longitude")
    @Expose
    private String Longitude;
    @SerializedName("PostalCode")
    @Expose
    private String PostalCode;
    @SerializedName("CityID")
    @Expose
    private String CityID;
    @SerializedName("ZoneNumber")
    @Expose
    private String ZoneNumber;
    @SerializedName("EmailAddress")
    @Expose
    private String EmailAddress;
    @SerializedName("TelegramAddress")
    @Expose
    private String TelegramAddress;
    @SerializedName("InstagramAddress")
    @Expose
    private String InstagramAddress;
    @SerializedName("BirthDate")
    @Expose
    private String BirthDate;
    @SerializedName("Image")
    @Expose
    private String Image;

    public String getUserID() {
        return UserID != null ? UserID: "";
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getFirstName() {
        return FirstName != null ? FirstName : "";
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName != null ? LastName : "";
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getStoreName() {
        return StoreName != null ? StoreName : "";
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getPhoneNumber() {
        return PhoneNumber != null ? PhoneNumber : "";
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address != null ? Address : "";
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLatitude() {
        return Latitude != null ? Latitude : "0";
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude != null ? Longitude : "0";
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getPostalCode() {
        return PostalCode != null ? PostalCode : "";
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getCityID() {
        return CityID != null ? CityID : "";
    }

    public void setCityID(String cityID) {
        CityID = cityID;
    }

    public String getZoneNumber() {
        return ZoneNumber != null ? ZoneNumber : "";
    }

    public void setZoneNumber(String zoneNumber) {
        ZoneNumber = zoneNumber;
    }

    public String getEmailAddress() {
        return EmailAddress != null ? EmailAddress : "";
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getTelegramAddress() {
        return TelegramAddress != null ? TelegramAddress : "";
    }

    public void setTelegramAddress(String telegramAddress) {
        TelegramAddress = telegramAddress;
    }

    public String getInstagramAddress() {
        return InstagramAddress != null ? InstagramAddress : "";
    }

    public void setInstagramAddress(String instagramAddress) {
        InstagramAddress = instagramAddress;
    }

    public String getBirthDate() {
        return BirthDate != null ? BirthDate : "";
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getImage() {
        return Image != null ? Image : "";
    }

    public void setImage(String image) {
        Image = image;
    }
}
