package com.koohpar.dstrbt.ui.profile;

import com.koohpar.dstrbt.data.DataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cmos on 05/04/2018.
 */
@Module
public class ProfileActivityModule {
    @Provides
    ProfileViewModel provideProfileViewModel(DataManager dataManager) {
        return new ProfileViewModel(dataManager);
    }
}
