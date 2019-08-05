package com.koohpar.dstrbt.ui.splash;

import com.koohpar.dstrbt.data.DataManager;
import com.google.android.gms.common.GoogleApiAvailability;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shb on 11/1/17.
 */
@Module
public class SplashActivityModule {

    @Provides
    SplashViewModel provideSplashViewModel(DataManager dataManager) {
        return new SplashViewModel(dataManager);
    }

    @Provides
    GoogleApiAvailability provideGoogleApiAvailability() {
        return GoogleApiAvailability.getInstance();
    }

}
