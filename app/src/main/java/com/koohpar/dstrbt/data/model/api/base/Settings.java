package com.koohpar.dstrbt.data.model.api.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Settings {

    private String success;
    private String message;
    private String pageSize;
    private List<String> fields = new ArrayList<String>();

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public boolean isSuccess() {
        if (success != null && (success.equalsIgnoreCase("1") || success.equalsIgnoreCase("2") || success.equalsIgnoreCase("3"))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isRequestSuccess() {
        if (success != null && (success.equalsIgnoreCase("1"))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasNextPage() {
        if (pageSize != null && (Integer.parseInt(pageSize) == 10)) {
            return true;
        } else {
            return false;
        }
    }

}

