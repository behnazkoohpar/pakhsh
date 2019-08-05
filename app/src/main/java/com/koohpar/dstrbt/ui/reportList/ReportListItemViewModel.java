package com.koohpar.dstrbt.ui.reportList;

import android.databinding.ObservableField;

import com.koohpar.dstrbt.data.model.api.ReportListResponse;
import com.koohpar.dstrbt.utils.fdate.DateUtil;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.text.NumberFormat;

/**
 * Created by cmos on 25/06/2018.
 */

public class ReportListItemViewModel {
    NumberFormat numberFormat = NumberFormat.getNumberInstance();

    public ObservableField<String> sumFactor = new ObservableField<>("");
    public ObservableField<String> dateFactor = new ObservableField<>("");
    public ObservableField<String> numberFactor = new ObservableField<>("");
    public ObservableField<String> stateFactor = new ObservableField<>("");

    public ReportListItemViewModel(ReportListResponse reportListResponse) {
        this.sumFactor.set(numberFormat.format(Integer.parseInt(reportListResponse.getSumPrice())));
        int year = Integer.parseInt(reportListResponse.getRequestDateTime().substring(0, 4));
        int month = Integer.parseInt(reportListResponse.getRequestDateTime().substring(5, 7));
        int day = Integer.parseInt(reportListResponse.getRequestDateTime().substring(8, 10));
//        JalaliCalendar.YearMonthDate yearMonthDate = new JalaliCalendar.YearMonthDate(year, month, day);
//        viewHolder.dateRequest.setText(JalaliCalendar.gregorianToJalali(yearMonthDate).toString());
        this.dateFactor.set(DateUtil.changeMiladiToFarsi(year + "/" + month + "/" + day));
        this.numberFactor.set(reportListResponse.getID());
        if (reportListResponse.getAccepted().equalsIgnoreCase("false")) {
            if (reportListResponse.getAcceptedDatetime() != null)
                this.stateFactor.set("رد درخواست");
            else
                this.stateFactor.set("در انتظار تائید");
        }
        if (reportListResponse.getAccepted().equalsIgnoreCase("true") &&
                reportListResponse.getAcceptedDatetime() != null)
            this.stateFactor.set("تائید شد");
        if (reportListResponse.getSended().equalsIgnoreCase("true") &&
                reportListResponse.getSendedDateTime() != null)
            this.stateFactor.set("ارسال شد");
    }
}
