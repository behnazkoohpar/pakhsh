package com.koohpar.dstrbt.ui.main;

import com.koohpar.dstrbt.data.DataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cmos on 05/04/2018.
 */
@Module
public class MainActivityModule {
    @Provides
    MainViewModel provideMainViewModel(DataManager dataManager) {
        return new MainViewModel(dataManager);
    }
}
