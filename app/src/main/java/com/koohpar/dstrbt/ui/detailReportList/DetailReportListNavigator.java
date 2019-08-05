package com.koohpar.dstrbt.ui.detailReportList;

import com.koohpar.dstrbt.data.model.api.DetailReportListResponse;

import java.util.List;

/**
 * Created by cmos on 25/06/2018.
 */

public interface DetailReportListNavigator {
    void setDetailReportList(List<DetailReportListResponse> detailRequestResponses);
    void callDeleteRequest();
    void openRequestList();
    void callDetailList();
}
