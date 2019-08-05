package com.koohpar.dstrbt.ui.listSelectedStuff;

import android.app.Activity;

import com.google.android.gms.maps.model.LatLng;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.api.BaseCallback;
import com.koohpar.dstrbt.api.ICallApi;
import com.koohpar.dstrbt.data.DataManager;
import com.koohpar.dstrbt.data.model.api.ProfileUserResponse;
import com.koohpar.dstrbt.data.model.api.base.Data;
import com.koohpar.dstrbt.data.model.api.database.StuffSelected;
import com.koohpar.dstrbt.ui.base.BaseViewModel;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Response;

/**
 * Created by cmos on 04/05/2018.
 */

public class ListSelectedStuffViewModel extends BaseViewModel<ListSelectedStuffNavigator> implements AppConstants {

    Activity mActivity;

    public ListSelectedStuffViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void onSetClick() {
        getNavigator().checkAdderess();
//        getNavigator().setOrder();
    }

    public void onDeleteClick() {
        getNavigator().setDelete();
    }

    public void callSetRequest(ICallApi iCallApi, ListSelectedStuffActivity context, HashMap<String, String> mParams) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, true, iCallApi, getDataManager(), API_CALL_SET_REQUEST, this);
            iCallApi.setRequest(mParams).enqueue(baseCallback);
            setIsLoading(true);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.not_can_call), null, null);
            e.printStackTrace();
        }
    }

    public void ProfileUser(ICallApi iCallApi, ListSelectedStuffActivity context, HashMap<String, String> map) {
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
            if (data.getSettings().getSuccess().equalsIgnoreCase("0")) {
                CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), data.getSettings().getMessage(), null, null);
            } else {
                if (data.getSettings().isSuccess()) {
                    switch (requestCode) {
                        case API_CALL_SET_REQUEST:
                            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), data.getSettings().getMessage(), mActivity.getString(R.string.ok), new CommonUtils.IL() {
                                @Override
                                public void onSuccess() {
                                    try {
                                        StuffSelected.deleteAll();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    mActivity.finish();
                                }

                                @Override
                                public void onCancel() {
                                    try {
                                        StuffSelected.deleteAll();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    mActivity.finish();
                                }
                            });
                            break;
                        case API_CALL_PROFILE_USER:
                            ProfileUserResponse profileUserResponse = (ProfileUserResponse) data.getData().get(0);
                            if (profileUserResponse.getAddress() != null &&
                                    !profileUserResponse.getAddress().equalsIgnoreCase("") &&
                                    !profileUserResponse.getAddress().equalsIgnoreCase("null"))
                                getNavigator().setOrder();

                            else
                                CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.set_address), mActivity.getString(R.string.btn_ok), new CommonUtils.IL() {
                                    @Override
                                    public void onSuccess() {
                                        getNavigator().openProfile();
                                    }

                                    @Override
                                    public void onCancel() {
                                        getNavigator().openProfile();
                                    }
                                });
                            break;
                    }
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
