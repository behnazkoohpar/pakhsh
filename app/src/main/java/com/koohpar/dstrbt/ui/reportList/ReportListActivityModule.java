package com.koohpar.dstrbt.ui.reportList;

import android.content.Context;

import com.koohpar.dstrbt.data.DataManager;
import com.koohpar.dstrbt.data.model.api.ReportListResponse;
import com.koohpar.dstrbt.data.model.api.base.Config;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cmos on 25/06/2018.
 */
@Module
public class ReportListActivityModule {
    @Provides
    ReportListViewModel provideReportListViewModel(DataManager dataManager) {
        return new ReportListViewModel(dataManager);
    }

    @Provides
    ReportListItemViewModel provideReportListItemViewModel(ReportListResponse reportListResponse) {
        return new ReportListItemViewModel(reportListResponse);
    }

    @Provides
    List<ReportListResponse> provideReportListResponse(){
        List<ReportListResponse> items = new ArrayList<>();
        return items ;
    }

    @Provides
    ReportListAdapter provideReportListAdapter(Context context , List<ReportListResponse> items){
        return new ReportListAdapter(context , items);
    }
}
