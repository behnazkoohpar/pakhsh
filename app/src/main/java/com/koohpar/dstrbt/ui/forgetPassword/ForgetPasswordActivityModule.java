package com.koohpar.dstrbt.ui.forgetPassword;

import com.koohpar.dstrbt.data.DataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by BehnazKoohpar on 2/5/2018.
 */
@Module
public class ForgetPasswordActivityModule {

    @Provides
    ForgetPasswordViewModel provideForgetPasswordViewModel(DataManager dataManager) {
        return new ForgetPasswordViewModel(dataManager);
    }
}
