package com.koohpar.dstrbt.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.DataManager;
import com.koohpar.dstrbt.data.model.api.ProfileUserResponse;
import com.koohpar.dstrbt.databinding.ActivityLoginBinding;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.ui.forgetPassword.ForgetPasswordActivity;
import com.koohpar.dstrbt.ui.main.MainActivity;
import com.koohpar.dstrbt.ui.signIn.SignInActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by shb on 11/3/17.
 */

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator, AppConstants {

    @Inject
    LoginViewModel mLoginViewModel;

    ActivityLoginBinding mActivityLoginBinding;
    private String username;
    private ImageView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = getViewDataBinding();
        mLoginViewModel.setNavigator(this);
        mLoginViewModel.setActivity(LoginActivity.this);
        show = (ImageView) findViewById(R.id.show);
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    public LoginViewModel getViewModel() {
        return mLoginViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void showPassword() {
        show.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mActivityLoginBinding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        mActivityLoginBinding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }
                return true;
            }
        });
    }

    @Override
    public void forgetPassword() {
        Intent intent = ForgetPasswordActivity.getStartIntent(LoginActivity.this);
        startActivity(intent);
    }

    @Override
    public void openSignInActivity() {
        Intent intent = SignInActivity.getStartIntent(LoginActivity.this);
        startActivity(intent);
    }

    @Override
    public void login() {
        try {
            if (TextUtils.isEmpty(mActivityLoginBinding.etUsername.getText())) {
                CommonUtils.showSingleButtonAlert(LoginActivity.this, getString(R.string.text_attention), getString(R.string.validation_phonenumber_or_email), getString(R.string.ok), null);
                return;
            } else if (!CommonUtils.isValidPhoneNumber(mActivityLoginBinding.etUsername.getText().toString())) {
                CommonUtils.showSingleButtonAlert(LoginActivity.this, getString(R.string.text_attention), getString(R.string.validation_phonenumber), getString(R.string.ok), null);
                return;
            } else if (TextUtils.isEmpty(mActivityLoginBinding.etPassword.getText())) {
                CommonUtils.showSingleButtonAlert(LoginActivity.this, getString(R.string.text_attention), getString(R.string.validation_password), getString(R.string.ok), null);
                return;
            } else if (mActivityLoginBinding.etPassword.getText().toString().length() < 6) {
                CommonUtils.showSingleButtonAlert(LoginActivity.this, getString(R.string.text_attention), getString(R.string.validation_password_length), getString(R.string.ok), null);
                return;
            }
            username = mActivityLoginBinding.etUsername.getText().toString();
            String password = mActivityLoginBinding.etPassword.getText().toString();

//            passwordE = TextEncrypter.MD5String(password);
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_MOBILE_NUMBER, username);
            map.put(REQUEST_KEY_PASSWORD, password);
            map.put(REQUEST_KEY_TOKEN, mLoginViewModel.getDataManager().getToken());
            if (LOGTRUE)
                Log.d("mPARAMS Login :::::::: ", map.toString());
            mLoginViewModel.login(iCallApi, LoginActivity.this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(LoginActivity.this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void callProfileUser() {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_MOBILE_NUMBER, username);
            if (LOGTRUE)
                Log.d("mPARAMS  :::::::: ", map.toString());
            mLoginViewModel.ProfileUser(iCallApi, LoginActivity.this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(LoginActivity.this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void setProfileParameter(ProfileUserResponse profileUserResponse) {
        mLoginViewModel.getDataManager().setCurrentUserLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER);
        mLoginViewModel.getDataManager().updateUserInfo(
                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                mActivityLoginBinding.etUsername.getText().toString(),
                mActivityLoginBinding.etPassword.getText().toString(),
                profileUserResponse.getFirstName(),
                profileUserResponse.getLastName(),
                profileUserResponse.getImage(),
                profileUserResponse.getUserID());
    }


    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.getStartIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void clearUserName() {
        mActivityLoginBinding.etUsername.setText("");
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
