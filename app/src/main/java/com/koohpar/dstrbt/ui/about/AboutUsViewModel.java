package com.koohpar.dstrbt.ui.about;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.koohpar.dstrbt.data.DataManager;
import com.koohpar.dstrbt.ui.base.BaseViewModel;
import com.koohpar.dstrbt.utils.AppConstants;

/**
 * Created by cmos on 15/06/2018.
 */

public class AboutUsViewModel extends BaseViewModel<AboutUsNavigator> {

    Activity mActivity;

    public AboutUsViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void callToMe(){
        Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("tel:02177335397"));
        mActivity.startActivity(i);
    }
    public void callToMe2(){
        Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("tel:02177335399"));
        mActivity.startActivity(i);
    }
    public void showSite(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.satakco.com/"));
        mActivity.startActivity(browserIntent);
    }
    @Override
    public void onResponseSuccess(Object mObject, int requestCode) {

    }

    @Override
    public void onResponseFailed(String errorMsg, int responseCode, int requestCode) {

    }

    @Override
    public void onFailedAuth(String errorMsg, int requestCode) {

    }
}
