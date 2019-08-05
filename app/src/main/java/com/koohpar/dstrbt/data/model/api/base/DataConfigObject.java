package com.koohpar.dstrbt.data.model.api.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BehnazKoohpar on 5/16/2017.
 */

public class DataConfigObject<T> extends BaseResponse {

    protected List<T> data = new ArrayList<>();
    protected Config config;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
