package com.koohpar.dstrbt.ui.main;

import android.app.Activity;

import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.api.BaseCallback;
import com.koohpar.dstrbt.api.ICallApi;
import com.koohpar.dstrbt.data.DataManager;
import com.koohpar.dstrbt.data.model.api.BannerResponse;
import com.koohpar.dstrbt.data.model.api.CategoryStuffResponse;
import com.koohpar.dstrbt.data.model.api.SpecialOfferResponse;
import com.koohpar.dstrbt.data.model.api.base.Data;
import com.koohpar.dstrbt.ui.base.BaseViewModel;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import java.util.HashMap;
import java.util.List;

import retrofit2.Response;

/**
 * Created by cmos on 05/04/2018.
 */

public class MainViewModel extends BaseViewModel<MainNavigator> implements AppConstants {

    Activity mActivity;

    public MainViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void onCallSearch() {
        getNavigator().callSearch();
    }



    public void getBanerList(ICallApi iCallApi, MainActivity context, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, false, iCallApi, getDataManager(), API_CALL_ALL_BANER_LIST, this);
            iCallApi.getAllBanerList(map).enqueue(baseCallback);
            setIsLoading(true);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.not_can_call), null, null);
            e.printStackTrace();
        }
    }

    public void getAllSpecialOffer(ICallApi iCallApi, MainActivity context, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, false, iCallApi, getDataManager(), API_CALL_ALL_SPECIAL_OFFER, this);
            iCallApi.getAllSpecialOffer(map).enqueue(baseCallback);
            setIsLoading(true);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.not_can_call), null, null);
            e.printStackTrace();
        }
    }

    public void setNotif(ICallApi iCallApi, MainActivity context, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, false, iCallApi, getDataManager(), API_CALL_SET_NOTIF, this);
            iCallApi.setnotif(map).enqueue(baseCallback);
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
                    case API_CALL_ALL_BANER_LIST:
                        List<BannerResponse> bannerResponses = data.getData();
                        getNavigator().setBanner(bannerResponses);
                        break;
                    case API_CALL_ALL_SPECIAL_OFFER:
                        List<SpecialOfferResponse> specialOfferResponses = data.getData();
                        getNavigator().setSpecialOffer(specialOfferResponses);
                        break;
                    case API_CALL_SET_NOTIF:
                        CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), data.getSettings().getMessage(), null, null);
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
