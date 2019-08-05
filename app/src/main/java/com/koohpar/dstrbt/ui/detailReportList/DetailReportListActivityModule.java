package com.koohpar.dstrbt.ui.detailReportList;

import android.content.Context;

import com.koohpar.dstrbt.data.DataManager;
import com.koohpar.dstrbt.data.model.api.DetailReportListResponse;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cmos on 25/06/2018.
 */
@Module
public class DetailReportListActivityModule {

    @Provides
    DetailReportListViewModel provideDetailReportListViewModel(DataManager dataManager) {
        return new DetailReportListViewModel(dataManager);
    }

    @Provides
    DetailReportListItemViewModel provideReportListItemViewModel(DetailReportListResponse reportListResponse) {
        return new DetailReportListItemViewModel(reportListResponse);
    }

    @Provides
    List<DetailReportListResponse> provideDetailReportListResponse(){
        List<DetailReportListResponse> items = new ArrayList<>();
        return items ;
    }

    @Provides
    DetailReportListAdapter provideDetailReportListAdapter(Context context , List<DetailReportListResponse> items){
        return new DetailReportListAdapter(context , items);
    }
}
