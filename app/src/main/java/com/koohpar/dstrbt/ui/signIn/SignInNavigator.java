package com.koohpar.dstrbt.ui.signIn;

/**
 * Created by cmos on 01/04/2018.
 */

public interface SignInNavigator {
    void clearTelNumber();
    void callSignIn();
    void openActivationActivity(String telNumber);
}
