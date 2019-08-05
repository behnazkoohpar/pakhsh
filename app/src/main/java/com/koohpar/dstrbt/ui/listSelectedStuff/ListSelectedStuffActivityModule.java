package com.koohpar.dstrbt.ui.listSelectedStuff;

import com.koohpar.dstrbt.data.DataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cmos on 04/05/2018.
 */
@Module
public class ListSelectedStuffActivityModule {
    @Provides
    ListSelectedStuffViewModel provideListSelectedStuffViewModel(DataManager dataManager) {
        return new ListSelectedStuffViewModel(dataManager);
    }
}
