package com.koohpar.dstrbt.ui.register;

import android.app.Activity;

import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.api.BaseCallback;
import com.koohpar.dstrbt.api.ICallApi;
import com.koohpar.dstrbt.data.DataManager;
import com.koohpar.dstrbt.data.model.api.MarketerListResponse;
import com.koohpar.dstrbt.data.model.api.ProfileUserResponse;
import com.koohpar.dstrbt.data.model.api.base.Data;
import com.koohpar.dstrbt.ui.base.BaseViewModel;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import java.util.HashMap;

import retrofit2.Response;

/**
 * Created by BehnazKoohpar on 1/7/2018.
 */

public class RegisterViewModel extends BaseViewModel<RegisterNavigator> implements AppConstants {

    Activity mActivity;

    public RegisterViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void onCallShowPassword() {
        getNavigator().showPassword();
    }

    public void onCallClearFamily() {
        getNavigator().clearFamily();
    }

    public void onCallClearName() {
        getNavigator().clearName();
    }

    public void onClickLocation() {
        getNavigator().openMapActivity();
    }

    public void onCallRegister() {
        getNavigator().callRegister();
    }
    public void onCallCheckCode() {
        getNavigator().callCheckCode();
    }

    public void Register(ICallApi iCallApi, RegisterActivity context, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, true, iCallApi, getDataManager(), API_CALL_REGISTER, this);
            iCallApi.register(map).enqueue(baseCallback);
            setIsLoading(true);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.not_can_call), null, null);
            e.printStackTrace();
        }
    }
    public void checkCode(ICallApi iCallApi, RegisterActivity context, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, true, iCallApi, getDataManager(), API_CALL_CHECK_CODE, this);
            iCallApi.checkCode(map).enqueue(baseCallback);
            setIsLoading(true);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.not_can_call), null, null);
            e.printStackTrace();
        }
    }

    public void ProfileUser(ICallApi iCallApi, RegisterActivity context, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, true, iCallApi, getDataManager(), API_CALL_PROFILE_USER, this);
            iCallApi.profileUser(map).enqueue(baseCallback);
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
            if (data.getSettings().getSuccess().toString().equalsIgnoreCase("0")) {
                CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), data.getSettings().getMessage(), null, null);
            } else {
                if (data != null) {
                    if (data.getSettings().isSuccess()) {
                        switch (requestCode) {
                            case API_CALL_REGISTER:
                                CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.congratulations), data.getSettings().getMessage(), null, new CommonUtils.IL() {
                                    @Override
                                    public void onSuccess() {
                                        getNavigator().callProfileUser();
                                    }

                                    @Override
                                    public void onCancel() {
                                        getNavigator().callProfileUser();
                                    }
                                });
                                break;

                            case API_CALL_PROFILE_USER:
                                ProfileUserResponse profileUserResponse = (ProfileUserResponse) data.getData().get(0);
                                getNavigator().setProfileParameter(profileUserResponse);
                                getNavigator().openMainActivity();
                                break;

                            case API_CALL_CHECK_CODE:
                                MarketerListResponse marketerListResponse = (MarketerListResponse) data.getData().get(0);
                                getNavigator().setMarketerListResponse(marketerListResponse);
                                break;
                        }
                    }
                } else
                    CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), data.getSettings().getMessage(), mActivity.getString(R.string.ok), null);
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