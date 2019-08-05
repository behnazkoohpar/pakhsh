package com.koohpar.dstrbt.di.module;

import android.app.Application;
import android.content.Context;

import com.koohpar.dstrbt.data.AppDataManager;
import com.koohpar.dstrbt.data.DataManager;
import com.koohpar.dstrbt.data.pref.AppPreferencesHelper;
import com.koohpar.dstrbt.data.pref.PreferencesHelper;
import com.koohpar.dstrbt.di.PreferenceInfo;
import com.koohpar.dstrbt.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shb on 10/29/2017.
 */

@Module
public class AppModule implements AppConstants {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

}
