package com.koohpar.dstrbt.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cmos on 25/06/2018.
 */

public class ReportListResponse {

    @SerializedName("ID")
    @Expose
    private String ID;

    @SerializedName("RequestDateTime")
    @Expose
    private String RequestDateTime;

    @SerializedName("StuffCount")
    @Expose
    private String StuffCount;

    @SerializedName("SumPrice")
    @Expose
    private String SumPrice;

    @SerializedName("Accepted")
    @Expose
    private String Accepted;

    @SerializedName("AcceptedDatetime")
    @Expose
    private String AcceptedDatetime;

    @SerializedName("Sended")
    @Expose
    private String Sended;

    @SerializedName("SendedDateTime")
    @Expose
    private String SendedDateTime;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getRequestDateTime() {
        return RequestDateTime;
    }

    public void setRequestDateTime(String requestDateTime) {
        RequestDateTime = requestDateTime;
    }

    public String getStuffCount() {
        return StuffCount;
    }

    public void setStuffCount(String stuffCount) {
        StuffCount = stuffCount;
    }

    public String getSumPrice() {
        return SumPrice;
    }

    public void setSumPrice(String sumPrice) {
        SumPrice = sumPrice;
    }

    public String getAccepted() {
        return Accepted;
    }

    public void setAccepted(String accepted) {
        Accepted = accepted;
    }

    public String getAcceptedDatetime() {
        return AcceptedDatetime;
    }

    public void setAcceptedDatetime(String acceptedDatetime) {
        AcceptedDatetime = acceptedDatetime;
    }

    public String getSended() {
        return Sended;
    }

    public void setSended(String sended) {
        Sended = sended;
    }

    public String getSendedDateTime() {
        return SendedDateTime;
    }

    public void setSendedDateTime(String sendedDateTime) {
        SendedDateTime = sendedDateTime;
    }
}
