package com.koohpar.dstrbt.ui.login;

import com.koohpar.dstrbt.data.DataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shb on 11/3/17.
 */
@Module
public class LoginActivityModule {

    @Provides
    LoginViewModel provideLoginViewModel(DataManager dataManager) {
        return new LoginViewModel(dataManager);
    }

}
