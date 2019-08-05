package com.koohpar.dstrbt.ui.profile;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.databinding.ActivityProfileBinding;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.ui.map.MapActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity<ActivityProfileBinding, ProfileViewModel> implements ProfileNavigator, AppConstants, com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    @Inject
    ProfileViewModel mProfileViewModel;

    ActivityProfileBinding mActivityProfileBinding;
    private CircleImageView imgProfile;
    private EditText name, family, birthDate, city, area, storeName,storeTel, postalCode, address, telegram, storeInsta, email;
    private DatePickerDialog datePickerDialog;
    private byte[] byteArrayImageProfile;
    private String encodedImage;
    public static LatLng latLng;
    public static EditText location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityProfileBinding = getViewDataBinding();
        mProfileViewModel.setNavigator(this);
        mProfileViewModel.setActivity(ProfileActivity.this);
        initView();
        callProfileUser();
    }

    private void initView() {

        imgProfile = (CircleImageView) findViewById(R.id.imgProfile);
        name = (EditText) findViewById(R.id.name);
        family = (EditText) findViewById(R.id.family);
        birthDate = (EditText) findViewById(R.id.birthDate);
        city = (EditText) findViewById(R.id.city);
        area = (EditText) findViewById(R.id.area);
        storeName = (EditText) findViewById(R.id.storeName);
        storeTel = (EditText) findViewById(R.id.storeTel);
        location = (EditText) findViewById(R.id.location);
        postalCode = (EditText) findViewById(R.id.postalCode);
        address = (EditText) findViewById(R.id.address);
        email = (EditText) findViewById(R.id.email);
        telegram = (EditText) findViewById(R.id.telegram);
        storeInsta = (EditText) findViewById(R.id.storeInsta);
        PersianCalendar persianCalendar = new PersianCalendar();
        datePickerDialog = DatePickerDialog.newInstance(
                this,
                persianCalendar.getPersianYear(),
                persianCalendar.getPersianMonth(),
                persianCalendar.getPersianDay()
        );
        datePickerDialog.setTypeface("iran_sans");

    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        return intent;
    }

    @Override
    public void showCalendar() {
        datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
        birthDate.setText(date);
    }

    @Override
    public void openDialogForPicture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkForCameraPermission()) {
                openCaptureDialog();
            }
        } else {
            openCaptureDialog();
        }
    }

    @TargetApi(23)
    boolean checkForCameraPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.CAMERA)) {
                CommonUtils.showTwoButtonAlert(this, getString(R.string.app_need_access_camera), getString(R.string.pop_up_ok), getString(R.string.cancel), new CommonUtils.IL() {
                    @Override
                    public void onSuccess() {
                        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                AppConstants.MY_PERMISSIONS_REQUEST_CAMERA);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                return false;
            }
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    AppConstants.MY_PERMISSIONS_REQUEST_CAMERA);

            return false;
        } else
            return true;
    }

    public void openCaptureDialog() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imgProfile.setImageURI(resultUri);
                Bitmap bmp = null;
                try {
                    bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                float ratio = (float) bmp.getWidth() / (float) bmp.getHeight();
                int w = bmp.getWidth() > bmp.getHeight() ? 500 : (int) (500 * ratio);
                int h = bmp.getWidth() < bmp.getHeight() ? 500 : (int) (500 * ratio);
                Bitmap resize = bmp.createScaledBitmap(bmp, w, h, false);
                resize.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byteArrayImageProfile = stream.toByteArray();
                encodedImage = Base64.encodeToString(byteArrayImageProfile, Base64.DEFAULT);
                callSetImageUser();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void callSetImageUser() {
        try {
                HashMap<String, String> map = new HashMap<>();
                map.put(REQUEST_KEY_USER_ID, mProfileViewModel.getDataManager().getUserId());
                map.put(REQUEST_KEY_IMAGE, encodedImage);

                if (LOGTRUE)
                    Log.d("mPARAMS Picture :::::::: ", map.toString());
                mProfileViewModel.editPicture(iCallApi, ProfileActivity.this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    public void callProfileUser() {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_MOBILE_NUMBER, mProfileViewModel.getDataManager().getUsername());
            if (LOGTRUE)
                Log.d("mPARAMS ProileUser :::::::: ", map.toString());
            mProfileViewModel.ProfileUser(iCallApi, this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public ProfileViewModel getViewModel() {
        return mProfileViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    public void openMapActivity() {
        Intent intent = MapActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void setImageProfile(String image) {
        if (image != null && !image.isEmpty() && !image.equalsIgnoreCase("null")) {
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imgProfile.setImageBitmap(decodedByte);
        }
    }

    @Override
    public void editPersonalInfo() {
        try {
            if (validatePeronInfo()) {
                HashMap<String, String> map = new HashMap<>();
                map.put(REQUEST_KEY_USER_ID, mProfileViewModel.getDataManager().getUserId());
                map.put(REQUEST_KEY_NAME, name.getText().toString());
                map.put(REQUEST_KEY_FAMILY, family.getText().toString());
                map.put(REQUEST_KEY_BIRTH_DATE, birthDate.getText().toString());

                if (LOGTRUE)
                    Log.d("mPARAMS Register :::::::: ", map.toString());
                mProfileViewModel.editProfile(iCallApi, ProfileActivity.this, map);
            }
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void editLocationAddress() {
        try {
            if (validate()) {
                HashMap<String, String> map = new HashMap<>();
                map.put(REQUEST_KEY_USER_ID, mProfileViewModel.getDataManager().getUserId());
                map.put(REQUEST_KEY_CITY, "24");
                map.put(REQUEST_KEY_AREA, area.getText().toString());
                map.put(REQUEST_KEY_ADDRESS, address.getText().toString());
                map.put(REQUEST_KEY_LATITUDE, String.valueOf(latLng.latitude));
                map.put(REQUEST_KEY_LONGITUDE, String.valueOf(latLng.longitude));
                map.put(REQUEST_KEY_STORE_NAME, storeName.getText().toString());
                map.put(REQUEST_KEY_STORE_TEL, storeTel.getText().toString());
                map.put(REQUEST_KEY_POSTAL_CODE, postalCode.getText().toString());

                if (LOGTRUE)
                    Log.d("mPARAMS Location :::::::: ", map.toString());
                mProfileViewModel.editLocation(iCallApi, ProfileActivity.this, map);
            }
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void editInternet() {
        try {
            if (validateInternet()) {
                HashMap<String, String> map = new HashMap<>();
                map.put(REQUEST_KEY_USER_ID, mProfileViewModel.getDataManager().getUserId());
                map.put(REQUEST_KEY_EMAIL, email.getText().toString());
                map.put(REQUEST_KEY_TELEGRAM, telegram.getText().toString());
                map.put(REQUEST_KEY_INSTAGRAM, storeInsta.getText().toString());

                if (LOGTRUE)
                    Log.d("mPARAMS Internet :::::::: ", map.toString());
                mProfileViewModel.editInternet(iCallApi, ProfileActivity.this, map);
            }
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    private boolean validate() {
        if (storeName.getText().toString().isEmpty()) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.validation_storename), getString(R.string.ok), null);
            return false;
        } else if (storeTel.getText().toString().isEmpty()) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.validation_storetel), getString(R.string.ok), null);
            return false;
        } else if (address.getText().toString().isEmpty()) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.validation_address), getString(R.string.ok), null);
            return false;
        }
        return true;
    }

    private boolean validatePeronInfo() {
        if (name.getText().toString().isEmpty()) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.validation_name), getString(R.string.ok), null);
            return false;
        } else if (family.getText().toString().isEmpty()) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.validation_family), getString(R.string.ok), null);
            return false;
        } else if (birthDate.getText().toString().isEmpty()) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.validation_birth_date), getString(R.string.ok), null);
            return false;
        }
        return true;
    }

    private boolean validateInternet() {
        if (email.getText().toString().isEmpty() && telegram.getText().toString().isEmpty() && storeInsta.getText().toString().isEmpty() ) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.validation_net), getString(R.string.ok), null);
            return false;
        }
        if(!email.getText().toString().isEmpty() && !CommonUtils.isEmailValid(email.getText().toString())){
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.validation_email), getString(R.string.ok), null);
            return false;
        }
        return true;
    }

    @Override
    public void setNewNameAndFamily() {
        mProfileViewModel.getDataManager().setFirstName(name.getText().toString());
        mProfileViewModel.getDataManager().setLastName(family.getText().toString());
    }

    @Override
    public void setNewImage() {
        mProfileViewModel.getDataManager().setProfilePicture(encodedImage);
    }

    @Override
    public void clearName() {
        name.setText("");
    }

    @Override
    public void clearFamily() {
        family.setText("");
    }

    @Override
    public void clearCity() {
        city.setText("");
    }

    @Override
    public void clearArea() {
        area.setText("");
    }

    @Override
    public void clearStoreName() {
        storeName.setText("");
    }

    @Override
    public void clearStoreTel() {
        storeTel.setText("");
    }

    @Override
    public void clearPostalCode() {
        postalCode.setText("");
    }

    @Override
    public void clearAddress() {
        address.setText("");
    }

    @Override
    public void clearEmail() {
        email.setText("");
    }

    @Override
    public void clearTelegram() {
        telegram.setText("");
    }

    @Override
    public void clearInstagram() {
        storeInsta.setText("");
    }
}
