package com.koohpar.dstrbt.ui.forgetPassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.databinding.ActivityForgetPasswordBinding;
import com.koohpar.dstrbt.ui.activation.ActivationActivity;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.ui.login.LoginActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import java.util.HashMap;

import javax.inject.Inject;

public class ForgetPasswordActivity extends BaseActivity<ActivityForgetPasswordBinding, ForgetPasswordViewModel> implements ForgetPasswordNavigator, AppConstants {

    @Inject
    ForgetPasswordViewModel mForgetPasswordViewModel;
    ActivityForgetPasswordBinding mActivityForgetPasswordBinding;
    private EditText tel_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityForgetPasswordBinding = getViewDataBinding();
        mForgetPasswordViewModel.setNavigator(this);
        mForgetPasswordViewModel.setActivity(ForgetPasswordActivity.this);
        initView();
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        return intent;
    }

    private void initView() {
        tel_number = (EditText) findViewById(R.id.tel_number);
    }

    @Override
    public ForgetPasswordViewModel getViewModel() {
        return mForgetPasswordViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void sendTelNumber() {
        try {
            if (validate()) {
                HashMap<String, String> mParams = new HashMap<String, String>();
                mParams.put(REQUEST_KEY_MOBILE_NUMBER, tel_number.getText().toString());
                if (LOGTRUE)
                    Log.d("mPARAMS LogOut :::::::: ", mParams.toString());
                mForgetPasswordViewModel.callSendTelNumber(iCallApi, ForgetPasswordActivity.this, mParams,tel_number.getText().toString());
            }
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(ForgetPasswordActivity.this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    private boolean validate() {
        if (CommonUtils.isTextContainOnlyNumber(tel_number.getText().toString()) && !CommonUtils.isValidPhoneNumber(tel_number.getText().toString())) {
            CommonUtils.showSingleButtonAlert(ForgetPasswordActivity.this, getString(R.string.text_attention), getString(R.string.validation_phonenumber), getString(R.string.ok), null);
            return false;
        }
        return true;
    }

    @Override
    public void startLoginActivity() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    @Override
    public void openActivationActivity(String telNumber) {
        ActivationActivity.TelNumber = telNumber;
        ActivationActivity.isFromForgotPassword = true;
        startActivity(new Intent(this,ActivationActivity.class));
        finish();
    }

    @Override
    public void onBackCall() {
        startActivity(new Intent(this,LoginActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,LoginActivity.class));
    }
}
