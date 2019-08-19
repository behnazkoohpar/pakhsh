package com.koohpar.dstrbt.ui.splash;

import com.koohpar.dstrbt.data.model.api.BannerResponse;

import java.util.List;

/**
 * Created by shb on 10/29/2017.
 */

public interface SplashNavigator {

    void openLoginActivity();
    void openMainActivity();
    void decideNextActivity();
    void invokeVersion(String androidFilePath);

    void setBanner(List<BannerResponse> bannerResponses);

    void getAllBanerList();
}
