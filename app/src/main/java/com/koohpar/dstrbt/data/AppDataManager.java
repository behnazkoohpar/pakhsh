package com.koohpar.dstrbt.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.koohpar.dstrbt.data.pref.PreferencesHelper;
import com.google.gson.Gson;

import javax.inject.Inject;


/**
 * Created by shb on 10/29/2017.
 */

public class AppDataManager implements DataManager {

    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(Context context,
                          PreferencesHelper preferencesHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public String getUsername() {
        return mPreferencesHelper.getUsername();
    }

    @Override
    public void setUsername(String username) {
        mPreferencesHelper.setUsername(username);
    }

    @Override
    public String getPassword() {
        return mPreferencesHelper.getPassword();
    }

    @Override
    public void setPassword(String password) {
        mPreferencesHelper.setPassword(password);
    }

    @Override
    public String getSecurityKey() {
        return mPreferencesHelper.getSecurityKey();
    }

    @Override
    public void setSecurityKey(String securityKey) {
        mPreferencesHelper.setSecurityKey(securityKey);
    }

    @Override
    public String getFirstName() {
        return mPreferencesHelper.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        mPreferencesHelper.setFirstName(firstName);
    }

    @Override
    public String getLastName() {
        return mPreferencesHelper.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        mPreferencesHelper.setLastName(lastName);
    }

    @Override
    public String getProfilePicture() {
        return mPreferencesHelper.getProfilePicture();
    }

    @Override
    public void setProfilePicture(String profilePicture) {
        mPreferencesHelper.setProfilePicture(profilePicture);
    }

    @Override
    public String getUserId() {
        return mPreferencesHelper.getUserId();
    }

    @Override
    public void setUserId(String userId) {
        mPreferencesHelper.setUserId(userId);
    }

    @Override
    public String getToken() {
        return mPreferencesHelper.getToken();
    }

    @Override
    public void setToken(String token) {
        mPreferencesHelper.setToken(token);
    }


    @Override
    public void updateUserInfo(
            LoggedInMode loggedInMode,
            String vUserName,
            String vPassword,
            String vFirstName,
            String vLastName,
            String vImage,
            String vUserID) {
        setCurrentUserLoggedInMode(loggedInMode);
        setUsername(vUserName);
        setPassword(vPassword);
        setFirstName(vFirstName);
        setLastName(vLastName);
        setProfilePicture(vImage);
        setUserId(vUserID);
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                "",
                "",
                "",
                "",
                "",
                "");
    }

}
