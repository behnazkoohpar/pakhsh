package com.koohpar.dstrbt.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cmos on 15/06/2018.
 */

public class GetLastVersionResponse {
    @SerializedName("AndroidVersion")
    @Expose
    private String AndroidVersion;
    @SerializedName("AndroidFilePath")
    @Expose
    private String AndroidFilePath;
    @SerializedName("iOSVersion")
    @Expose
    private String iOSVersion;
    @SerializedName("iOSFilePath")
    @Expose
    private String iOSFilePath;

    public String getAndroidVersion() {
        return AndroidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        AndroidVersion = androidVersion;
    }

    public String getAndroidFilePath() {
        return AndroidFilePath;
    }

    public void setAndroidFilePath(String androidFilePath) {
        AndroidFilePath = androidFilePath;
    }

    public String getiOSVersion() {
        return iOSVersion;
    }

    public void setiOSVersion(String iOSVersion) {
        this.iOSVersion = iOSVersion;
    }

    public String getiOSFilePath() {
        return iOSFilePath;
    }

    public void setiOSFilePath(String iOSFilePath) {
        this.iOSFilePath = iOSFilePath;
    }
}
