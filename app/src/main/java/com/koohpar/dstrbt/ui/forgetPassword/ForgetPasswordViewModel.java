package com.koohpar.dstrbt.ui.forgetPassword;

import android.app.Activity;

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
 * Created by BehnazKoohpar on 2/5/2018.
 */

public class ForgetPasswordViewModel extends BaseViewModel<ForgetPasswordNavigator> implements AppConstants {

    Activity mActivity;
    private String TelNumber;

    public ForgetPasswordViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void onBackCall(){
        getNavigator().onBackCall();
    }

    public void onCallSendTelNumber() {
        getNavigator().sendTelNumber();
    }

    public void callSendTelNumber(ICallApi iCallApi, ForgetPasswordActivity context, HashMap<String, String> mParams,String telNumber) {
        try {
            TelNumber = telNumber;
            BaseCallback baseCallback = new BaseCallback(context, true, iCallApi, getDataManager(), API_CALL_SEND_FORGET_PASSWORD, this);
            iCallApi.sendForgetPassword(mParams).enqueue(baseCallback);
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
                if (data.getSettings().isSuccess()) {

                    switch (requestCode) {
                        case (API_CALL_SEND_FORGET_PASSWORD):
                            CommonUtils.showMessageButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.text_send_message), R.drawable.email, new CommonUtils.IL() {
                                @Override
                                public void onSuccess() {
                                    getNavigator().openActivationActivity(TelNumber);
                                }

                                @Override
                                public void onCancel() {
                                    getNavigator().startLoginActivity();
                                }
                            });
                            break;
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

    }

    @Override
    public void onFailedAuth(String errorMsg, int requestCode) {

    }
}
