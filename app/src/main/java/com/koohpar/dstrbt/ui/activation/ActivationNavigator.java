package com.koohpar.dstrbt.ui.activation;

/**
 * Created by cmos on 02/04/2018.
 */

public interface ActivationNavigator {
    void callActivation();
    void openRegisterActivity(String telNumber);
    void clearActivation();
    void openNewPasswordActivity();
    void callResendCode();
}
