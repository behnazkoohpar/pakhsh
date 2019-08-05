package com.koohpar.dstrbt.ui.about;

import com.koohpar.dstrbt.data.DataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cmos on 15/06/2018.
 */
@Module
public class AboutUsActivityModule {
    @Provides
    AboutUsViewModel provideAboutUsViewModel(DataManager dataManager) {
        return new AboutUsViewModel(dataManager);
    }
}
