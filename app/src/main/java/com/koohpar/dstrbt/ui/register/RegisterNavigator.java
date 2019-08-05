package com.koohpar.dstrbt.ui.register;

import com.koohpar.dstrbt.data.model.api.ProfileUserResponse;

/**
 * Created by BehnazKoohpar on 1/7/2018.
 */

public interface RegisterNavigator {

    void callRegister();
    void showPassword();
    void openMapActivity();
    void openMainActivity();
    void clearFamily();
    void clearName();
    void setProfileParameter(ProfileUserResponse profileUserResponse);
    void callProfileUser();
}
