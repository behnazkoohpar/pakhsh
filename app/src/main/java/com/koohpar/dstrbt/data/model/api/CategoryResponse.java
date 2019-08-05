package com.koohpar.dstrbt.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cmos on 13/04/2018.
 */

public class CategoryResponse {
    @SerializedName("ID")
    @Expose
    private String ID;
    @SerializedName("ParentID")
    @Expose
    private String ParentID;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Activated")
    @Expose
    private String Activated;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getParentID() {
        return ParentID;
    }

    public void setParentID(String parentID) {
        ParentID = parentID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getActivated() {
        return Activated;
    }

    public void setActivated(String activated) {
        Activated = activated;
    }
}
