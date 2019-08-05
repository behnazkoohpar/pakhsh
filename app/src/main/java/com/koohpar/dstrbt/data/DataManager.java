package com.koohpar.dstrbt.data;

import com.koohpar.dstrbt.data.pref.PreferencesHelper;

/**
 * Created by shb on 10/29/2017.
 */

public interface DataManager extends PreferencesHelper {

    void setUserAsLoggedOut();
    void updateUserInfo(
            LoggedInMode loggedInMode,
            String vUserName,
            String vPassword,
            String vFirstName,
            String vLastName,
            String vImage,
            String vUserId);

    enum LoggedInMode {
        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_SERVER(1);
        private final int mType;
        LoggedInMode(int type) {
            mType = type;
        }
        public int getType() {
            return mType;
        }
    }

}
