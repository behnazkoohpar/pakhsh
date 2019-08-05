package com.koohpar.dstrbt.ui.login;

import com.koohpar.dstrbt.data.model.api.ProfileUserResponse;
import com.koohpar.dstrbt.data.model.api.base.Data;

/**
 * Created by shb on 11/3/17.
 */

public interface LoginNavigator {

    void login();
    void showPassword();
    void forgetPassword();
    void openSignInActivity();
    void openMainActivity();
    void clearUserName();
    void callProfileUser();
    void setProfileParameter(ProfileUserResponse data);
}
