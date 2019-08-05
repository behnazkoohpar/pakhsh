package com.koohpar.dstrbt.ui.detailReportList;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.koohpar.dstrbt.data.model.api.DetailReportListResponse;

import java.text.NumberFormat;

/**
 * Created by cmos on 25/06/2018.
 */

public class DetailReportListItemViewModel {

    NumberFormat numberFormat = NumberFormat.getNumberInstance();

    public ObservableField<String> nameStuff = new ObservableField<>("");
    public ObservableField<String> price = new ObservableField<>("");
    public ObservableField<String> sumprice = new ObservableField<>("");
    public ObservableField<String> offer = new ObservableField<>("");
    public ObservableField<String> consumerPrice = new ObservableField<>("");
    public ObservableField<String> number = new ObservableField<>("");
    public ObservableBoolean isDelete = new ObservableBoolean(false);
    public ObservableBoolean isEdit = new ObservableBoolean(false);

    public DetailReportListItemViewModel(DetailReportListResponse reportListResponse) {
        this.nameStuff.set(reportListResponse.getStuffBrandName());
        this.sumprice.set(numberFormat.format(Integer.parseInt(reportListResponse.getSumPrice())));
        this.consumerPrice.set(numberFormat.format(Integer.parseInt(reportListResponse.getConsumerPrice())));
        this.offer.set(reportListResponse.getOffer());
        this.price.set(numberFormat.format(Integer.parseInt(reportListResponse.getPrice())));
        this.number.set(reportListResponse.getCount());
        if(DetailReportListActivity.detailReportList.getAccepted().equalsIgnoreCase("false"))
            isDelete.set(true);
        if(DetailReportListActivity.detailReportList.getAccepted().equalsIgnoreCase("false"))
            isEdit.set(true);
    }
}
