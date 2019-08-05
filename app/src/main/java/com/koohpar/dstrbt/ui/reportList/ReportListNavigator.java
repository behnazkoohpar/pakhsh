package com.koohpar.dstrbt.ui.reportList;

import com.koohpar.dstrbt.data.model.api.ReportListResponse;

import java.util.List;

/**
 * Created by cmos on 25/06/2018.
 */

public interface ReportListNavigator {
    void setReportList(List<ReportListResponse> profileUserResponses);
}
