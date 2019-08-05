package com.koohpar.dstrbt.data.pref;

import com.koohpar.dstrbt.data.DataManager;

/**
 * Created by shb on 10/29/2017.
 */

public interface PreferencesHelper {

    int getCurrentUserLoggedInMode();
    void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode);
    String getUsername();
    void setUsername(String username);
    String getPassword();
    void setPassword(String password);
    String getSecurityKey();
    void setSecurityKey(String userId);
    String getFirstName();
    void setFirstName(String firstName);
    String getLastName();
    void setLastName(String lastName);
    String getProfilePicture();
    void setProfilePicture(String profilePicture);
    String getUserId();
    void setUserId(String userId);
    String getToken();
    void setToken(String token);
}
