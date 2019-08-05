package com.koohpar.dstrbt.ui.brand;

import com.koohpar.dstrbt.data.DataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cmos on 14/04/2018.
 */
@Module
public class BrandActivityModule {
    @Provides
    BrandViewModel provideBrandViewModel(DataManager dataManager) {
        return new BrandViewModel(dataManager);
    }
}
