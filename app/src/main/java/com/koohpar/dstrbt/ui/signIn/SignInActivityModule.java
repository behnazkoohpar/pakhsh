package com.koohpar.dstrbt.ui.signIn;

import com.koohpar.dstrbt.data.DataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cmos on 01/04/2018.
 */
@Module
public class SignInActivityModule {
    @Provides
    SignInViewModel provideSignInViewModel(DataManager dataManager) {
        return new SignInViewModel(dataManager);
    }

}
