package com.koohpar.dstrbt.ui.activation;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.databinding.ActivityActivationBinding;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.ui.newPassword.NewPasswordActivity;
import com.koohpar.dstrbt.ui.register.RegisterActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import java.util.HashMap;

import javax.inject.Inject;

public class ActivationActivity extends BaseActivity<ActivityActivationBinding, ActivationViewModel> implements ActivationNavigator, AppConstants {

    @Inject
    ActivationViewModel mActivationViewModel;

    ActivityActivationBinding mActivityActivationBinding;
    private ImageView clear;
    private EditText activation;
    public static String TelNumber;
    public static boolean isFromForgotPassword = false;

    private CountDownTimer countDownTimer;
    private int numberCallSms = 1;
    private Handler mHandler;
    private final int mInterval = 2000;

    @Override
    public ActivationViewModel getViewModel() {
        return mActivationViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_activation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityActivationBinding = getViewDataBinding();
        mActivationViewModel.setNavigator(this);
        mActivationViewModel.setActivity(ActivationActivity.this);
        clear = (ImageView) findViewById(R.id.clear);
        activation = (EditText) findViewById(R.id.activation);
        mHandler = new Handler();
        setCountDownTimer();
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ActivationActivity.class);
        return intent;
    }

    @Override
    public void callActivation() {
        try {
            if (TextUtils.isEmpty(activation.getText())) {
                CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.validation_activation), getString(R.string.ok), null);
                return;
            } else if (activation.getText().toString().length() != 6) {
                CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.validation_activation_true), getString(R.string.ok), null);
                return;
            }
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_MOBILE_NUMBER, TelNumber);
            map.put(REQUEST_KEY_ACTIVATION_CODE, activation.getText().toString());
            if (LOGTRUE)
                Log.d("mPARAMS  Activation :::::::: ", map.toString());
            mActivationViewModel.callActivation(iCallApi, ActivationActivity.this, map, TelNumber);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void openRegisterActivity(String telNumber) {
        RegisterActivity.TelNumber = telNumber;
        Intent intent = RegisterActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void clearActivation() {
        activation.setText("");
    }

    @Override
    public void openNewPasswordActivity() {
        Intent intent = NewPasswordActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void callResendCode() {
        if (numberCallSms >= 3) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.number_request_more_than_three), getString(R.string.btn_ok), null);
            return;
        }
        mActivityActivationBinding.smsNotRecieve.setVisibility(View.GONE);
        mActivityActivationBinding.smsTime.setVisibility(View.VISIBLE);
        setCountDownTimer();
        callSignIn();
        numberCallSms++;
    }

    private void setCountDownTimer() {
        countDownTimer = new CountDownTimer((30000), 1000) {
            private int minutes, seconds;
            private String minutesStr, secondsStr;

            public void onTick(long millisUntilFinished) {
                seconds = (int) (millisUntilFinished / 1000 % 60);
                minutes = (int) (millisUntilFinished / 1000 / 60);
                minutesStr = String.valueOf(minutes);
                secondsStr = String.valueOf(seconds);

                if (seconds < 10) secondsStr = "0" + secondsStr;
                if (minutes < 10) minutesStr = "0" + minutesStr;
                mActivityActivationBinding.timerText.setText(minutesStr + ":" + secondsStr);
            }

            public void onFinish() {
                mActivityActivationBinding.smsNotRecieve.setVisibility(View.VISIBLE);
                mActivityActivationBinding.smsTime.setVisibility(View.GONE);
            }

        }.start();
    }

    public void callSignIn() {
        try {

            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_MOBILE_NUMBER, TelNumber);
            if (LOGTRUE)
                Log.d("mPARAMS Login  ", map.toString());
            mActivationViewModel.callSignIn(iCallApi, ActivationActivity.this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
