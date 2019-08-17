package com.koohpar.dstrbt.ui.register;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.DataManager;
import com.koohpar.dstrbt.data.model.api.MarketerListResponse;
import com.koohpar.dstrbt.data.model.api.ProfileUserResponse;
import com.koohpar.dstrbt.databinding.ActivityRegisterBinding;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.ui.main.MainActivity;
import com.koohpar.dstrbt.ui.map.MapActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import java.util.HashMap;

import javax.inject.Inject;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> implements RegisterNavigator, AppConstants {

    public static String TelNumber;
    @Inject
    RegisterViewModel mRegisterViewModel;

    ActivityRegisterBinding mActivityRegisterBinding;
    private ImageView show;
    private EditText password, telNumber, name, family;
    private String idMarketer;

    @Override
    public RegisterViewModel getViewModel() {
        return mRegisterViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRegisterBinding = getViewDataBinding();
        mRegisterViewModel.setNavigator(this);
        mRegisterViewModel.setActivity(RegisterActivity.this);
        show = (ImageView) findViewById(R.id.show);
        password = (EditText) findViewById(R.id.password);
        telNumber = (EditText) findViewById(R.id.telNumber);
        telNumber.setText(TelNumber);
        name = (EditText) findViewById(R.id.name);
        family = (EditText) findViewById(R.id.family);
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void showPassword() {
        show.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        password.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void openMapActivity() {
        Intent intent = MapActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void clearFamily() {
        family.setText("");
    }

    @Override
    public void clearName() {
        name.setText("");
    }

    @Override
    public void callRegister() {
        try {
            if (validate()) {
                HashMap<String, String> map = new HashMap<>();
//                String passwordE = TextEncrypter.MD5String(password.getText().toString());
                map.put(REQUEST_KEY_PASSWORD, password.getText().toString());
                map.put(REQUEST_KEY_MOBILE_NUMBER, telNumber.getText().toString());
                map.put(REQUEST_KEY_NAME, name.getText().toString());
                map.put(REQUEST_KEY_FAMILY, family.getText().toString());
                map.put(REQUEST_KEY_CODE, idMarketer);

                if (LOGTRUE)
                    Log.d("mPARAMS Register :::::::: ", map.toString());
                mRegisterViewModel.Register(iCallApi, RegisterActivity.this, map);
            }
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(RegisterActivity.this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void callProfileUser() {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_MOBILE_NUMBER, TelNumber);
            if (LOGTRUE)
                Log.d("mPARAMS ProfileUser :::::::: ", map.toString());
            mRegisterViewModel.ProfileUser(iCallApi, RegisterActivity.this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(RegisterActivity.this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void callCheckCode() {
        try {
            if (mActivityRegisterBinding.code.getText().toString().isEmpty()) {
                CommonUtils.showSingleButtonAlert(RegisterActivity.this, getString(R.string.text_attention), getString(R.string.code_is_null), null, null);
                return;
            }
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_CODE, mActivityRegisterBinding.code.getText().toString());
            if (LOGTRUE)
                Log.d("mPARAMS callCheckCode :::::::: ", map.toString());
            mRegisterViewModel.checkCode(iCallApi, RegisterActivity.this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(RegisterActivity.this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    private boolean validate() {
        try {
            if (name.getText().toString().isEmpty()) {
                CommonUtils.showSingleButtonAlert(RegisterActivity.this, getString(R.string.text_attention), getString(R.string.please_enter_name), null, null);
                return false;
            }
            if (family.getText().toString().isEmpty()) {
                CommonUtils.showSingleButtonAlert(RegisterActivity.this, getString(R.string.text_attention), getString(R.string.please_enter_family), null, null);
                return false;
            }
            if (password.getText().toString().isEmpty()) {
                CommonUtils.showSingleButtonAlert(RegisterActivity.this, getString(R.string.text_attention), getString(R.string.please_enter_password), null, null);
                return false;
            }

            if (telNumber.getText().toString().isEmpty()) {
                CommonUtils.showSingleButtonAlert(RegisterActivity.this, getString(R.string.text_attention), getString(R.string.please_enter_tel_number), null, null);
                return false;
            }

            if (!CommonUtils.isValidPhoneNumber(telNumber.getText().toString())) {
                CommonUtils.showSingleButtonAlert(RegisterActivity.this, getString(R.string.text_attention), getString(R.string.please_enter_right_tel_number), null, null);
                return false;
            }

        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(RegisterActivity.this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void setProfileParameter(ProfileUserResponse profileUserResponse) {
        mRegisterViewModel.getDataManager().setCurrentUserLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER);
        mRegisterViewModel.getDataManager().updateUserInfo(
                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                TelNumber,
                password.getText().toString(),
                profileUserResponse.getFirstName(),
                profileUserResponse.getLastName(),
                profileUserResponse.getImage(),
                profileUserResponse.getUserID());
    }

    @Override
    public void setMarketerListResponse(MarketerListResponse marketerListResponse) {
        idMarketer = marketerListResponse.getID();
        CommonUtils.showSingleButtonAlert(RegisterActivity.this, getString(R.string.text_attention),
                "آقای "+marketerListResponse.getFirstName()+" "+marketerListResponse.getLastName()+" . تلفن همراه:  "+marketerListResponse.getPhoneNumber(),
                null, null);
        mActivityRegisterBinding.code.setEnabled(false);
        mActivityRegisterBinding.btnCheckCode.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
