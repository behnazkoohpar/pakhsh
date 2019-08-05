package com.koohpar.dstrbt.ui.newPassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.databinding.ActivityNewPasswordBinding;
import com.koohpar.dstrbt.ui.activation.ActivationActivity;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.ui.login.LoginActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import java.util.HashMap;

import javax.inject.Inject;

public class NewPasswordActivity extends BaseActivity<ActivityNewPasswordBinding, NewPasswordViewModel> implements NewPasswordNavigator, AppConstants {

    @Inject
    NewPasswordViewModel mNewPasswordViewModel;

    ActivityNewPasswordBinding mActivityNewPasswordBinding;

    @Override
    public NewPasswordViewModel getViewModel() {
        return mNewPasswordViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityNewPasswordBinding = getViewDataBinding();
        mNewPasswordViewModel.setNavigator(this);
        mNewPasswordViewModel.setActivity(NewPasswordActivity.this);
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, NewPasswordActivity.class);
        return intent;
    }

    @Override
    public void clearPassword() {
        mActivityNewPasswordBinding.newPassword.setText("");
    }

    @Override
    public void clearRePassword() {
        mActivityNewPasswordBinding.reNewPassword.setText("");
    }

    @Override
    public void callChangePassword() {
        try {
            if (TextUtils.isEmpty(mActivityNewPasswordBinding.reNewPassword.getText())) {
                CommonUtils.showSingleButtonAlert(NewPasswordActivity.this, getString(R.string.text_attention), getString(R.string.validation_re_password), getString(R.string.ok), null);
                return;
            } else if (TextUtils.isEmpty(mActivityNewPasswordBinding.newPassword.getText().toString())) {
                CommonUtils.showSingleButtonAlert(NewPasswordActivity.this, getString(R.string.text_attention), getString(R.string.validation_password), getString(R.string.ok), null);
                return;
            } else if (mActivityNewPasswordBinding.newPassword.getText().toString().length() < 6) {
                CommonUtils.showSingleButtonAlert(NewPasswordActivity.this, getString(R.string.text_attention), getString(R.string.validation_password_length), getString(R.string.ok), null);
                return;
            } else if (mActivityNewPasswordBinding.reNewPassword.getText().toString().length() < 6) {
                CommonUtils.showSingleButtonAlert(NewPasswordActivity.this, getString(R.string.text_attention), getString(R.string.validation_re_password_length), getString(R.string.ok), null);
                return;
            }else if (!mActivityNewPasswordBinding.reNewPassword.getText().toString().equalsIgnoreCase(mActivityNewPasswordBinding.newPassword.getText().toString())) {
                CommonUtils.showSingleButtonAlert(NewPasswordActivity.this, getString(R.string.text_attention), getString(R.string.validation_two_password), getString(R.string.ok), null);
                return;
            }
//            passwordE = TextEncrypter.MD5String(password);
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_NEW_PASSWORD, mActivityNewPasswordBinding.newPassword.getText().toString());
            map.put(REQUEST_KEY_PHONE, ActivationActivity.TelNumber);
            if (LOGTRUE)
                Log.d("mPARAMS newPassword::::", map.toString());
            mNewPasswordViewModel.callChangePassword(iCallApi, NewPasswordActivity.this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(NewPasswordActivity.this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.getStartIntent(NewPasswordActivity.this);
        startActivity(intent);
        finish();
    }
}
