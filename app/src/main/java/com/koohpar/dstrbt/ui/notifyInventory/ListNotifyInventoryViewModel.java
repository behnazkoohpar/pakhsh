package com.koohpar.dstrbt.ui.notifyInventory;

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

public class ListNotifyInventoryViewModel extends BaseViewModel<ListNotifyInventoryNavigator> implements AppConstants {

    Activity mActivity;

    public ListNotifyInventoryViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void getNitifyInventoryList(ListNotifyInventoryActivity context, ICallApi iCallApi, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, true, iCallApi, getDataManager(), API_CALL_GET_NOTIFY_INVENTORY_LIST, this);
            iCallApi.getNotifyInventory(map).enqueue(baseCallback);
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
                        case API_CALL_GET_NOTIFY_INVENTORY_LIST:
                            List<CategoryStuffResponse> categoryStuffResponses =  data.getData();
                            getNavigator().setListNotify(categoryStuffResponses);
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

