package com.koohpar.dstrbt.ui.profile;

import android.app.Activity;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.google.android.gms.maps.model.LatLng;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.api.BaseCallback;
import com.koohpar.dstrbt.api.ICallApi;
import com.koohpar.dstrbt.data.DataManager;
import com.koohpar.dstrbt.data.model.api.ProfileUserResponse;
import com.koohpar.dstrbt.data.model.api.base.Data;
import com.koohpar.dstrbt.ui.base.BaseViewModel;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import java.util.HashMap;

import retrofit2.Response;

/**
 * Created by cmos on 05/04/2018.
 */

public class ProfileViewModel extends BaseViewModel<ProfileNavigator> implements AppConstants {

    Activity mActivity;
    public final ObservableField<String> name = new ObservableField();
    public final ObservableField<String> family = new ObservableField();
    public final ObservableField<String> birtDate = new ObservableField();
    public final ObservableField<String> city = new ObservableField();
    public final ObservableField<String> area = new ObservableField();
    public final ObservableField<String> storeName = new ObservableField();
    public final ObservableField<String> storeTel = new ObservableField();
    public final ObservableField<String> location = new ObservableField();
    public final ObservableField<String> postalCode = new ObservableField();
    public final ObservableField<String> address = new ObservableField();
    public final ObservableField<String> email = new ObservableField();
    public final ObservableField<String> telegram = new ObservableField();
    public final ObservableField<String> instagram = new ObservableField();
    public final ObservableBoolean isPersonLayout = new ObservableBoolean(true);
    public final ObservableBoolean isLocationLayout = new ObservableBoolean();
    public final ObservableBoolean isInternetLayout = new ObservableBoolean();

    public ProfileViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void openPersonLayout() {
        isPersonLayout.set(true);
        isInternetLayout.set(false);
        isLocationLayout.set(false);
    }

    public void openInternetLayout() {
        isPersonLayout.set(false);
        isInternetLayout.set(true);
        isLocationLayout.set(false);
    }

    public void openLocationLayout() {
        isPersonLayout.set(false);
        isInternetLayout.set(false);
        isLocationLayout.set(true);
    }

    public void onCallClearName() {
        getNavigator().clearName();
    }

    public void onCallClearFamily() {
        getNavigator().clearFamily();
    }

    public void onCallCalendar() {
        getNavigator().showCalendar();
    }

    public void setNewPicture() {
        getNavigator().openDialogForPicture();
    }

    public void onCallEditProfile() {
        getNavigator().editPersonalInfo();
    }

    public void onCallClearCity() {
        getNavigator().clearCity();
    }

    public void onCallClearArea() {
        getNavigator().clearArea();
    }

    public void onCallClearStoreName() {
        getNavigator().clearStoreName();
    }

    public void onCallClearStoreTel() {
        getNavigator().clearStoreTel();
    }

    public void onOpenMap() {
        getNavigator().openMapActivity();
    }

    public void onCallClearPostalCode() {
        getNavigator().clearPostalCode();
    }

    public void onCallClearAddress() {
        getNavigator().clearAddress();
    }

    public void onCallEditLocation() {
        getNavigator().editLocationAddress();
    }

    public void onCallClearEmail() {
        getNavigator().clearEmail();
    }

    public void onCallClearTelegram() {
        getNavigator().clearTelegram();
    }

    public void onCallClearInstagram() {
        getNavigator().clearInstagram();
    }

    public void onCallEditInternet() {
        getNavigator().editInternet();
    }

    public void ProfileUser(ICallApi iCallApi, ProfileActivity context, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, true, iCallApi, getDataManager(), API_CALL_PROFILE_USER, this);
            iCallApi.profileUser(map).enqueue(baseCallback);
            setIsLoading(true);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.not_can_call), null, null);
            e.printStackTrace();
        }
    }

    public void editProfile(ICallApi iCallApi, ProfileActivity context, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, true, iCallApi, getDataManager(), API_CALL_EDIT_PROFILE_USER, this);
            iCallApi.editProfileUser(map).enqueue(baseCallback);
            setIsLoading(true);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.not_can_call), null, null);
            e.printStackTrace();
        }
    }

    public void editPicture(ICallApi iCallApi, ProfileActivity context, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, true, iCallApi, getDataManager(), API_CALL_EDIT_PICTURE, this);
            iCallApi.editPicture(map).enqueue(baseCallback);
            setIsLoading(true);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.not_can_call), null, null);
            e.printStackTrace();
        }
    }

    public void editLocation(ICallApi iCallApi, ProfileActivity context, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, true, iCallApi, getDataManager(), API_CALL_EDIT_LOCATION, this);
            iCallApi.editLocation(map).enqueue(baseCallback);
            setIsLoading(true);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), mActivity.getString(R.string.not_can_call), null, null);
            e.printStackTrace();
        }
    }

    public void editInternet(ICallApi iCallApi, ProfileActivity context, HashMap<String, String> map) {
        try {
            BaseCallback baseCallback = new BaseCallback(context, true, iCallApi, getDataManager(), API_CALL_EDIT_INTERNET, this);
            iCallApi.editInternet(map).enqueue(baseCallback);
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
                    case API_CALL_PROFILE_USER:
                        ProfileUserResponse profileUserResponse = (ProfileUserResponse) data.getData().get(0);
                        name.set(profileUserResponse.getFirstName());
                        family.set(profileUserResponse.getLastName());
                        birtDate.set(profileUserResponse.getBirthDate());
                        city.set(profileUserResponse.getCityID());
                        area.set(profileUserResponse.getZoneNumber());
                        storeName.set(profileUserResponse.getStoreName());
                        storeTel.set(profileUserResponse.getPhoneNumber());
                        location.set(profileUserResponse.getLatitude() + "," + profileUserResponse.getLongitude());
                        ProfileActivity.latLng= new LatLng(Double.parseDouble(profileUserResponse.getLatitude()) , Double.parseDouble(profileUserResponse.getLongitude()));
                        postalCode.set(profileUserResponse.getPostalCode());
                        address.set(profileUserResponse.getAddress());
                        email.set(profileUserResponse.getEmailAddress());
                        telegram.set(profileUserResponse.getTelegramAddress());
                        instagram.set(profileUserResponse.getInstagramAddress());
                        getNavigator().setImageProfile(profileUserResponse.getImage());
                        break;
                    case API_CALL_EDIT_PROFILE_USER:
                        getNavigator().setNewNameAndFamily();
                        CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), data.getSettings().getMessage(), null, null);
                        break;
                    case API_CALL_EDIT_PICTURE:
                        getNavigator().setNewImage();
                        CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), data.getSettings().getMessage(), null, null);
                        break;
                    case API_CALL_EDIT_LOCATION:
                        CommonUtils.showSingleButtonAlert(mActivity, mActivity.getString(R.string.text_attention), data.getSettings().getMessage(), null, null);
                        break;
                    case API_CALL_EDIT_INTERNET:
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
