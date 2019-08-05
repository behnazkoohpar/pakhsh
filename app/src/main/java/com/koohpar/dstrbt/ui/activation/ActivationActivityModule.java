package com.koohpar.dstrbt.ui.activation;

import com.koohpar.dstrbt.data.DataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cmos on 02/04/2018.
 */
@Module
public class ActivationActivityModule {
    @Provides
    ActivationViewModel provideActivationViewModel(DataManager dataManager) {
        return new ActivationViewModel(dataManager);
    }
}
