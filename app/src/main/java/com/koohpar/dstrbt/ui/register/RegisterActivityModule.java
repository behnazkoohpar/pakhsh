package com.koohpar.dstrbt.ui.register;

import com.koohpar.dstrbt.data.DataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by BehnazKoohpar on 1/7/2018.
 */
@Module
public class RegisterActivityModule {
    @Provides
    RegisterViewModel provideRegisterViewModel(DataManager dataManager) {
        return new RegisterViewModel(dataManager);
    }
}
