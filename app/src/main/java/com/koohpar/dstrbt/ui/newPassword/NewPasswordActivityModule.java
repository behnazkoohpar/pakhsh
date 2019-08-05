package com.koohpar.dstrbt.ui.newPassword;

import com.koohpar.dstrbt.data.DataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cmos on 19/06/2018.
 */

@Module
public class NewPasswordActivityModule {
    @Provides
    NewPasswordViewModel provideNewPasswordViewModel(DataManager dataManager) {
        return new NewPasswordViewModel(dataManager);
    }
}
