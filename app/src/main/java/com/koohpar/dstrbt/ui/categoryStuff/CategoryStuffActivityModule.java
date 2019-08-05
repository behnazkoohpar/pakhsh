package com.koohpar.dstrbt.ui.categoryStuff;

import com.koohpar.dstrbt.data.DataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cmos on 14/04/2018.
 */
@Module
public class CategoryStuffActivityModule {
    @Provides
    CategoryStuffViewModel provideCategoryStuffViewModel(DataManager dataManager) {
        return new CategoryStuffViewModel(dataManager);
    }
}
