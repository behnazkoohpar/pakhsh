package com.koohpar.dstrbt.ui.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.databinding.ActivityAboutUsBinding;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.utils.AppConstants;

import javax.inject.Inject;

public class AboutUsActivity extends BaseActivity<ActivityAboutUsBinding, AboutUsViewModel> implements AboutUsNavigator, AppConstants {

    @Inject
    AboutUsViewModel mAboutUsViewModel;

    ActivityAboutUsBinding mActivityAboutUsBinding;

    @Override
    public AboutUsViewModel getViewModel() {
        return mAboutUsViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAboutUsBinding = getViewDataBinding();
        mAboutUsViewModel.setNavigator(this);
        mAboutUsViewModel.setActivity(AboutUsActivity.this);
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, AboutUsActivity.class);
        return intent;
    }

}
