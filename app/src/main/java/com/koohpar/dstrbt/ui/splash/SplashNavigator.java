package com.koohpar.dstrbt.ui.splash;

/**
 * Created by shb on 10/29/2017.
 */

public interface SplashNavigator {

    void openLoginActivity();
    void openMainActivity();
    void decideNextActivity();
    void invokeVersion(String androidFilePath);
}
