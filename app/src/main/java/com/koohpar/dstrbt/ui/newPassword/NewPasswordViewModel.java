package com.koohpar.dstrbt.ui.newPassword;

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
 * Created by cmos on 19/06/2018.
 */

public class NewPasswordViewModel extends BaseViewModel<NewPasswordNavigator> implements AppConstants {

    Activity mActivity;

    public NewPasswordViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void onCallClearPassword() {
        getNavigator().clearPassword();
    }

    public void onCallClearRePassword() {
        getNavigator().clearRePassword();
    }

    public void onChangePasswordClick() {
        getNavigator().callChangePassword();
    }

    public void callChangePassword(ICallApi iCallApi, NewPasswordActivity context, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, true, iCallApi, getDataManager(), API_CALL_CHANGE_PASSWORD, this);
            iCallApi.forgetPassword(map).enqueue(baseCallback);
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
                    case API_CALL_CHANGE_PASSWORD:
                        if (data.getSettings().isSuccess()) {
                            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.change_password_ok), mActivity.getString(R.string.ok), new CommonUtils.IL() {
                                @Override
                                public void onSuccess() {
                                    getNavigator().openLoginActivity();
                                }

                                @Override
                                public void onCancel() {
                                    getNavigator().openLoginActivity();
                                }
                            });


                        } else {
                            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), data.getSettings().getMessage(), mActivity.getString(R.string.ok), null);
                        }
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

    }

    @Override
    public void onFailedAuth(String errorMsg, int requestCode) {

    }

}
