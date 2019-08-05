package com.koohpar.dstrbt.ui.activation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.api.BaseCallback;
import com.koohpar.dstrbt.api.ICallApi;
import com.koohpar.dstrbt.data.DataManager;
import com.koohpar.dstrbt.data.model.api.base.Data;
import com.koohpar.dstrbt.ui.base.BaseViewModel;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import java.util.HashMap;

import retrofit2.Response;

/**
 * Created by cmos on 02/04/2018.
 */

public class ActivationViewModel extends BaseViewModel<ActivationNavigator> implements AppConstants {

    Activity mActivity;
    private String TelNumber;

    public ActivationViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void onCallClearActivation() {
        getNavigator().clearActivation();
    }

    public void onActivationClick() {
        getNavigator().callActivation();
    }

    public void reSendCode() {
        getNavigator().callResendCode();
    }

    public void callToCenter() {
        Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("tel:02177335399"));
        mActivity.startActivity(i);
    }

    public void callActivation(ICallApi iCallApi, ActivationActivity context, HashMap<String, String> map, String telNumber) {
        try {
            TelNumber = telNumber;
            BaseCallback baseCallback = new BaseCallback(context, true, iCallApi, getDataManager(), API_CALL_ACTIVATION, this);
            iCallApi.activation(map).enqueue(baseCallback);
            setIsLoading(true);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.not_can_call), null, null);
            e.printStackTrace();
        }
    }

    public void callSignIn(ICallApi iCallApi, ActivationActivity context, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, true, iCallApi, getDataManager(), API_CALL_SIGN_IN, this);
            iCallApi.signIn(map).enqueue(baseCallback);
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
                    case API_CALL_ACTIVATION:
                        if (data.getSettings().isSuccess()) {
                            if (ActivationActivity.isFromForgotPassword)
                                getNavigator().openNewPasswordActivity();
                            else
                                getNavigator().openRegisterActivity(TelNumber);
                        } else {
                            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), data.getSettings().getMessage(), mActivity.getString(R.string.ok), null);
                        }
                        break;

                    case API_CALL_SIGN_IN:
                        CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), "پیامک فعالسازی ارسال شد", null, null);
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
