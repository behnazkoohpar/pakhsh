package com.koohpar.dstrbt.ui.signIn;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.databinding.ActivitySignInBinding;
import com.koohpar.dstrbt.ui.activation.ActivationActivity;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import java.util.HashMap;

import javax.inject.Inject;

public class SignInActivity extends BaseActivity<ActivitySignInBinding, SignInViewModel> implements SignInNavigator, AppConstants {

    @Inject
    SignInViewModel mSignInViewModel;

    ActivitySignInBinding mActivitySignInBinding;
    private ImageView clear;
    private EditText telNumber;

    @Override
    public SignInViewModel getViewModel() {
        return mSignInViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySignInBinding = getViewDataBinding();
        mSignInViewModel.setNavigator(this);
        mSignInViewModel.setActivity(SignInActivity.this);
        clear = (ImageView) findViewById(R.id.clear);
        telNumber = (EditText) findViewById(R.id.telNumber);
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        return intent;
    }

    @Override
    public void clearTelNumber() {
        telNumber.setText("");
    }

    @Override
    public void callSignIn() {
        try {
            if (TextUtils.isEmpty(telNumber.getText())) {
                CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.validation_phonenumber_or_email), getString(R.string.ok), null);
                return;
            } else if (!CommonUtils.isValidPhoneNumber(telNumber.getText().toString())) {
                CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.validation_phonenumber), getString(R.string.ok), null);
                return;
            }
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_MOBILE_NUMBER, telNumber.getText().toString());
            if (LOGTRUE)
                Log.d("mPARAMS Login :::::::: ", map.toString());
            mSignInViewModel.callSignIn(iCallApi, SignInActivity.this, map,telNumber.getText().toString());
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void openActivationActivity(String telNumber) {
        ActivationActivity.TelNumber = telNumber;
        Intent intent = ActivationActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
