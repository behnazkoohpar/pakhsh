package com.koohpar.dstrbt.ui.notifyInventory;

import com.koohpar.dstrbt.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class ListNotifyInventoryActivityModule {
    @Provides
    ListNotifyInventoryViewModel provideListNotifyInventoryViewModel(DataManager dataManager) {
        return new ListNotifyInventoryViewModel(dataManager);
    }
}
