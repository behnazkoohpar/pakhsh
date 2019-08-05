package com.koohpar.dstrbt.data.model.api.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandeeppatel on 9/25/15.
 */
public class Data<T> extends BaseResponse {

    protected List<T> data = new ArrayList<T>();

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
