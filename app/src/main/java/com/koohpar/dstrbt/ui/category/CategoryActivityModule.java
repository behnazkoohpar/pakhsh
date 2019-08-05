package com.koohpar.dstrbt.ui.category;

import com.koohpar.dstrbt.data.DataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cmos on 13/04/2018.
 */
@Module
public class CategoryActivityModule {
    @Provides
    CategoryViewModel provideCategoryViewModel(DataManager dataManager) {
        return new CategoryViewModel(dataManager);
    }
}
