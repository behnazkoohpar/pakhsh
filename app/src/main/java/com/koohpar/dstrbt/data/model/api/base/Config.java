package com.koohpar.dstrbt.data.model.api.base;

/**
 * Created by BehnazKoohpar on 5/16/2017.
 */

public class Config {
    private String SUPPORT_EMAIL;
    private String SUPPORT_NUMBER;
    private String WAITING_TIME_TO_RIDE_PASSENGER;
    private String TIME_DRIVER_UPDATE_LOCATION ="60000";
    private String TIME_UPDATE_RIDE_PATH;
    private String CALL_CENTER;

    public String getCALL_CENTER() {
        return CALL_CENTER;
    }

    public void setCALL_CENTER(String CALL_CENTER) {
        this.CALL_CENTER = CALL_CENTER;
    }

    public String getSUPPORT_EMAIL() {
        return SUPPORT_EMAIL;
    }

    public void setSUPPORT_EMAIL(String SUPPORT_EMAIL) {
        this.SUPPORT_EMAIL = SUPPORT_EMAIL;
    }


    public String getTIME_DRIVER_UPDATE_LOCATION() {
        return TIME_DRIVER_UPDATE_LOCATION;
    }

    public void setTIME_DRIVER_UPDATE_LOCATION(String TIME_DRIVER_UPDATE_LOCATION) {
        this.TIME_DRIVER_UPDATE_LOCATION = TIME_DRIVER_UPDATE_LOCATION;
    }

    public String getTIME_UPDATE_RIDE_PATH() {
        return TIME_UPDATE_RIDE_PATH;
    }

    public void setTIME_UPDATE_RIDE_PATH(String TIME_UPDATE_RIDE_PATH) {
        this.TIME_UPDATE_RIDE_PATH = TIME_UPDATE_RIDE_PATH;
    }

    public String getSUPPORT_NUMBER() {
        return SUPPORT_NUMBER;
    }

    public void setSUPPORT_NUMBER(String SUPPORT_NUMBER) {
        this.SUPPORT_NUMBER = SUPPORT_NUMBER;
    }

    public String getWAITING_TIME_TO_RIDE_PASSENGER() {
        return WAITING_TIME_TO_RIDE_PASSENGER;
    }

    public void setWAITING_TIME_TO_RIDE_PASSENGER(String WAITING_TIME_TO_RIDE_PASSENGER) {
        this.WAITING_TIME_TO_RIDE_PASSENGER = WAITING_TIME_TO_RIDE_PASSENGER;
    }
}
