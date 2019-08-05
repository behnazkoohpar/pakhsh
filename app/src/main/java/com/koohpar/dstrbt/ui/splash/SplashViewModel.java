package com.koohpar.dstrbt.ui.splash;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.api.BaseCallback;
import com.koohpar.dstrbt.api.ICallApi;
import com.koohpar.dstrbt.data.DataManager;
import com.koohpar.dstrbt.data.model.api.GetLastVersionResponse;
import com.koohpar.dstrbt.data.model.api.base.Data;
import com.koohpar.dstrbt.ui.base.BaseViewModel;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Response;

/**
 * Created by shb on 10/29/2017.
 */

public class SplashViewModel extends BaseViewModel<SplashNavigator> implements AppConstants {

    Activity mActivity;
    private int version;
    private PackageInfo pInfo;

    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public SplashViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void callGetlastVersion(ICallApi iCallApi, SplashActivity context, HashMap<String, String> map) {
        try {

            BaseCallback baseCallback = new BaseCallback(context, false, iCallApi, getDataManager(), API_CALL_GET_LAST_VERSION, this);
            iCallApi.getLastVersion(map).enqueue(baseCallback);
            setIsLoading(true);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.not_can_call), null, null);
            e.printStackTrace();
        }
    }

    public void login(ICallApi iCallApi, SplashActivity context, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, true, iCallApi, getDataManager(), API_CALL_LOGIN, this);
            iCallApi.login(map).enqueue(baseCallback);
            setIsLoading(true);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.not_can_call), null, null);
            e.printStackTrace();
        }
    }
    @Override
    public void onResponseSuccess(Object mObject, int requestCode) {
        try {
            Response response = (Response) mObject;
            Data data = (Data) response.body();
            if (data.getSettings().getSuccess().equalsIgnoreCase("0")) {
                CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), data.getSettings().getMessage(), null, null);
            } else {
                switch (requestCode) {
                    case API_CALL_GET_LAST_VERSION:
                        List<GetLastVersionResponse> getLastVersionResponses = data.getData();
                        PackageInfo pInfo = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0);
                        final int version = pInfo.versionCode;
                        if (!getLastVersionResponses.get(0).getAndroidVersion().isEmpty() && Integer.parseInt(getLastVersionResponses.get(0).getAndroidVersion()) > version) {
                            getNavigator().invokeVersion(getLastVersionResponses.get(0).getAndroidFilePath());
                        } else
                            getNavigator().decideNextActivity();
                        break;
                    case API_CALL_LOGIN:
                        getNavigator().openMainActivity();
                        break;
                }
            }
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.problem), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void onResponseFailed(String errorMsg, int responseCode, int requestCode) {
        setIsLoading(false);
        CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.api_response_failed), mActivity.getString(R.string.btn_ok), null);
    }

    @Override
    public void onFailedAuth(String errorMsg, int requestCode) {
        setIsLoading(false);
        CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.authentication_failed), mActivity.getString(R.string.btn_ok), null);
    }


}
