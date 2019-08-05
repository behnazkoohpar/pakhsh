package com.koohpar.dstrbt.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.inject.Inject;

/**
 * Created by shb on 10/31/2017.
 */

public class BaseHeaderHelper {

    private ProtectedApiHeader mProtectedApiHeader;

    @Inject
    public BaseHeaderHelper(ProtectedApiHeader protectedApiHeader) {
        mProtectedApiHeader = protectedApiHeader;
    }

    public ProtectedApiHeader getProtectedApiHeader() {
        return mProtectedApiHeader;
    }

    public static final class ProtectedApiHeader {

        @Expose
        @SerializedName("api_key")
        private String mApiKey;

        @Expose
        @SerializedName("user_id")
        private Long mUserId;

        @Expose
        @SerializedName("access_token")
        private String mAccessToken;

        public ProtectedApiHeader(String mApiKey, Long mUserId, String mAccessToken) {
            this.mApiKey = mApiKey;
            this.mUserId = mUserId;
            this.mAccessToken = mAccessToken;
        }

        public String getApiKey() {
            return mApiKey;
        }

        public void setApiKey(String apiKey) {
            mApiKey = apiKey;
        }

        public Long getUserId() {
            return mUserId;
        }

        public void setUserId(Long mUserId) {
            this.mUserId = mUserId;
        }

        public String getAccessToken() {
            return mAccessToken;
        }

        public void setAccessToken(String accessToken) {
            mAccessToken = accessToken;
        }
    }

}
