package com.koohpar.dstrbt.di.component;

import android.app.Application;

import com.koohpar.dstrbt.App;
import com.koohpar.dstrbt.di.builder.ActivityBuilder;
import com.koohpar.dstrbt.di.module.AppModule;
import com.koohpar.dstrbt.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by shb on 10/29/2017.
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class , AppModule.class, ActivityBuilder.class ,  NetworkModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
    void inject(App app);

}
