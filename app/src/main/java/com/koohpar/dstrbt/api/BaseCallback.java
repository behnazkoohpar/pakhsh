package com.koohpar.dstrbt.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.koohpar.dstrbt.BuildConfig;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.DataManager;
import com.koohpar.dstrbt.data.model.api.base.Data;
import com.koohpar.dstrbt.data.model.api.base.DataConfigObject;
import com.koohpar.dstrbt.ui.activation.ActivationActivity;
import com.koohpar.dstrbt.ui.login.LoginActivity;
import com.koohpar.dstrbt.ui.register.RegisterActivity;
import com.koohpar.dstrbt.ui.splash.SplashActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.AppLogger;
import com.koohpar.dstrbt.utils.CommonUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shb on 10/31/2017.
 */

public class BaseCallback implements Callback, AppConstants {

    private boolean showProgressbar;
    private ProgressDialog progressDialog;
    private DataManager dataManager;

    private static final String TAG_AUTH_FAILED = "Authentication Failed";

    private Context mContext;
    private IAPIResponse mApiResponse;
    private ICallApi iCallApi;
    private int requestCode;


    public BaseCallback(Context mContext, boolean showProgressbar, ICallApi iCallApi, DataManager dataManager, int requestCode, IAPIResponse mApiResponse) {
        try {
            this.mContext = mContext;
            this.mApiResponse = mApiResponse;
            this.requestCode = requestCode;
            this.iCallApi = iCallApi;
            this.dataManager = dataManager;
            this.showProgressbar = showProgressbar;
            if (showProgressbar) {
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void login(Call call) {
//        try {
//            HashMap<String, String> map = new HashMap<>();
//            map.put(REQUEST_KEY_PASSWORD, dataManager.getPassword());
//            iCallApi.login(map).enqueue(this);
//        } catch (Exception e) {
//            CommonUtils.showSingleButtonAlert(mContext, mContext.getString(R.string.text_attention),  mContext.getString(R.string.data_incorrect), null, null);
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onResponse(Call call, Response response) {
        try {
            if (this.showProgressbar)
                progressDialog.dismiss();
            Data data = null;
            if (response.code() == 200 || response.code() == 201) {
//                if (requestCode == 1001) {
//                    dataConfigObject = (DataConfigObject) response.body();
//                    if (TextUtils.equals(dataConfigObject.getSettings().getSuccess(), "2")) {
//                        mContext.startActivity(new Intent(mContext, SplashActivity.class));
//                    } else if (TextUtils.equals(dataConfigObject.getSettings().getSuccess(), "0")) {
//                        CommonUtils.showSingleButtonAlert(mContext, mContext.getString(R.string.text_attention), dataConfigObject.getSettings().getMessage(), null, null);
//                    } else {
//                        mApiResponse.onResponseSuccess(response, requestCode);
//                    }
//                } else {
                    data = (Data) response.body();
                    if (TextUtils.equals(data.getSettings().getSuccess(), "2")) {
                        mContext.startActivity(new Intent(mContext, SplashActivity.class));

                    } else if (TextUtils.equals(data.getSettings().getSuccess(), "0")) {
                        CommonUtils.showSingleButtonAlert(mContext, mContext.getString(R.string.text_attention), data.getSettings().getMessage(), null, null);
                    } else {
                        mApiResponse.onResponseSuccess(response, requestCode);
                    }
//                }
            } else
                mApiResponse.onResponseFailed(response.errorBody().toString(), response.code(), requestCode);
        } catch (Exception e) {
            if (!this.showProgressbar)
                return;
            progressDialog.dismiss();
            CommonUtils.showSingleButtonAlert(mContext, mContext.getString(R.string.text_attention), mContext.getString(R.string.problem), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        try {
            if (!this.showProgressbar)
                return;
            progressDialog.dismiss();
            AppLogger.d("Request Error : Request failed In Callback");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
