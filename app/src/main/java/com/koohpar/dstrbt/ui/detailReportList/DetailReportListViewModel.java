package com.koohpar.dstrbt.ui.detailReportList;

import android.app.Activity;

import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.api.BaseCallback;
import com.koohpar.dstrbt.api.ICallApi;
import com.koohpar.dstrbt.data.DataManager;
import com.koohpar.dstrbt.data.model.api.DetailReportListResponse;
import com.koohpar.dstrbt.data.model.api.base.Data;
import com.koohpar.dstrbt.ui.base.BaseViewModel;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import java.util.HashMap;
import java.util.List;

import retrofit2.Response;

/**
 * Created by cmos on 25/06/2018.
 */

public class DetailReportListViewModel extends BaseViewModel<DetailReportListNavigator>
        implements AppConstants {

    Activity mActivity;

    public DetailReportListViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void onCallDelete() {
        getNavigator().callDeleteRequest();
    }

    public void getDetailReportList(DetailReportListActivity context, ICallApi iCallApi, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, false, iCallApi, getDataManager(), API_CALL_DETAIL_REQUEST, this);
            iCallApi.getDetailReport(map).enqueue(baseCallback);
            setIsLoading(true);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.not_can_call), null, null);
            e.printStackTrace();
        }
    }

    public void deleteDetailReportList(DetailReportListActivity context, ICallApi iCallApi, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, false, iCallApi, getDataManager(), API_CALL_DELETE_DETAIL_REQUEST, this);
            iCallApi.deleteRequest(map).enqueue(baseCallback);
            setIsLoading(true);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.not_can_call), null, null);
            e.printStackTrace();
        }
    }

    public void deleteItemDetailReportList(DetailReportListActivity context, ICallApi iCallApi, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, false, iCallApi, getDataManager(), API_CALL_DELETE_ITEM_DETAIL_REQUEST, this);
            iCallApi.deleteItemRequest(map).enqueue(baseCallback);
            setIsLoading(true);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.not_can_call), null, null);
            e.printStackTrace();
        }
    }
    public void editItemDetailReportList(DetailReportListActivity context, ICallApi iCallApi, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, false, iCallApi, getDataManager(), API_CALL_EDIT_ITEM_DETAIL_REQUEST, this);
            iCallApi.editItemRequest(map).enqueue(baseCallback);
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
                    case API_CALL_DETAIL_REQUEST:
                        List<DetailReportListResponse> detailRequestResponses = data.getData();
                        getNavigator().setDetailReportList(detailRequestResponses);
                        break;
                    case API_CALL_DELETE_DETAIL_REQUEST:
                        getNavigator().openRequestList();
                        break;
                    case API_CALL_DELETE_ITEM_DETAIL_REQUEST:
                        getNavigator().callDetailList();
                        break;
                    case API_CALL_EDIT_ITEM_DETAIL_REQUEST:
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
