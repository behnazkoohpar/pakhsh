package com.koohpar.dstrbt.ui.brand;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.model.api.BrandResponse;
import com.koohpar.dstrbt.databinding.ActivityBrandBinding;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class BrandActivity extends BaseActivity<ActivityBrandBinding, BrandViewModel> implements BrandNavigator, AppConstants {

    @Inject
    BrandViewModel mBrandViewModel;

    ActivityBrandBinding mActivityBrandBinding;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<BrandResponse> banerList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBrandBinding = getViewDataBinding();
        mBrandViewModel.setNavigator(this);
        mBrandViewModel.setActivity(BrandActivity.this);
        initView();
        callGetAllBrand();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.brand_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, BrandActivity.class);
        return intent;
    }

    @Override
    public BrandViewModel getViewModel() {
        return mBrandViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_brand;
    }

    private void callGetAllBrand() {
        try {
            HashMap<String, String> map = new HashMap<>();
            if (LOGTRUE)
                Log.d("mPARAMS Brand :::::::: ", map.toString());
            mBrandViewModel.getAllBrand(iCallApi, this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void setBrand(List<BrandResponse> brandResponses) {

        banerList.addAll(brandResponses);
        recyclerView.setAdapter(new BrandRecycleViewAdapter(banerList));
    }
}
