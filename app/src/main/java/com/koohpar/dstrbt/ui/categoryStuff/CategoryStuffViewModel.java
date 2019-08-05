package com.koohpar.dstrbt.ui.categoryStuff;

import android.app.Activity;

import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.api.BaseCallback;
import com.koohpar.dstrbt.api.ICallApi;
import com.koohpar.dstrbt.data.DataManager;
import com.koohpar.dstrbt.data.model.api.CategoryStuffResponse;
import com.koohpar.dstrbt.data.model.api.base.Data;
import com.koohpar.dstrbt.ui.base.BaseViewModel;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import java.util.HashMap;
import java.util.List;

import retrofit2.Response;

/**
 * Created by cmos on 14/04/2018.
 */

public class CategoryStuffViewModel extends BaseViewModel<CategoryStuffNavigator> implements AppConstants {

    Activity mActivity;

    public CategoryStuffViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void getAllCategoryStuff(ICallApi iCallApi, CategoryStuffActivity context, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, true, iCallApi, getDataManager(), API_CALL_ALL_CATEGORY_STUFF, this);
            iCallApi.getAllCategoryStuff(map).enqueue(baseCallback);
            setIsLoading(true);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.not_can_call), null, null);
            e.printStackTrace();
        }
    }

    public void getAllCategoryStuffByBrand(ICallApi iCallApi, CategoryStuffActivity context, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, true, iCallApi, getDataManager(), API_CALL_ALL_CATEGORY_STUFF_BY_BRAND, this);
            iCallApi.getAllCategoryStuffByBrand(map).enqueue(baseCallback);
            setIsLoading(true);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.not_can_call), null, null);
            e.printStackTrace();
        }
    }

    public void getSearchKeyword(ICallApi iCallApi, CategoryStuffActivity context, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, true, iCallApi, getDataManager(), API_CALL_ALL_SEARCH, this);
            iCallApi.getSearchByKeyword(map).enqueue(baseCallback);
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
                    case API_CALL_ALL_CATEGORY_STUFF:
                        List<CategoryStuffResponse> categoryStuffResponses = data.getData();
                        getNavigator().setCategoryStuff(categoryStuffResponses);
                        break;
                    case API_CALL_ALL_CATEGORY_STUFF_BY_BRAND:
                        List<CategoryStuffResponse> categoryStuffResponses2 = data.getData();
                        getNavigator().setCategoryStuff(categoryStuffResponses2);
                        break;
                    case API_CALL_ALL_SEARCH:
                        List<CategoryStuffResponse> categoryStuffResponses3 = data.getData();
                        getNavigator().setCategoryStuff(categoryStuffResponses3);
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
