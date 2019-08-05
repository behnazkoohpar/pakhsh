package com.koohpar.dstrbt;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.koohpar.dstrbt.di.component.DaggerAppComponent;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.AppLogger;
import com.orm.SugarApp;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by shb on 10/29/2017.
 */

public class App extends SugarApp implements HasActivityInjector {

    public static Context context;
    public static SharedPreferences preferences;
    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE);
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
        AppLogger.init();

    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

}
