package com.koohpar.dstrbt.ui.forgetPassword;

/**
 * Created by BehnazKoohpar on 2/5/2018.
 */

public interface ForgetPasswordNavigator {

    void sendTelNumber();
    void startLoginActivity();
    void openActivationActivity(String telNumber);
    void onBackCall();
}
