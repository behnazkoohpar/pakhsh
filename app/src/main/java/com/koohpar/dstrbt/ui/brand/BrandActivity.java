package com.koohpar.dstrbt.ui.brand;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.model.api.BrandResponse;
import com.koohpar.dstrbt.databinding.ActivityBrandBinding;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;
import com.koohpar.dstrbt.utils.EndlessRecyclerViewScrollListener;

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
    private EndlessRecyclerViewScrollListener scrollListenerSuggestion;

    private int firstVisiblePositionSuggestion;
    private int findFirstCompletelyVisibleItemPositionSuggestion;
    private int findLastVisibleItemPositionSuggestion;
    private int findLastCompletelyVisibleItemPositionSuggestion;
    private int pageNum = 0;
    private int next = 10;
    private BrandRecycleViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBrandBinding = getViewDataBinding();
        mBrandViewModel.setNavigator(this);
        mBrandViewModel.setActivity(BrandActivity.this);
        initView();
        callGetAllBrand(next);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.brand_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new BrandRecycleViewAdapter(banerList);
        recyclerView.setAdapter(mAdapter);
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

    private void callGetAllBrand(int next) {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_OFFSER, String.valueOf(pageNum));
            map.put(REQUEST_KEY_NEXT, String.valueOf(next));
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
        mAdapter.notifyDataSetChanged();
        layoutManager.scrollToPositionWithOffset(firstVisiblePositionSuggestion, pageNum);
        pageNum = pageNum + next;
        scrollListenerSuggestion = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                firstVisiblePositionSuggestion = layoutManager.findFirstVisibleItemPosition();
                findFirstCompletelyVisibleItemPositionSuggestion = layoutManager.findFirstCompletelyVisibleItemPosition();
                findLastVisibleItemPositionSuggestion = layoutManager.findLastVisibleItemPosition();
                findLastCompletelyVisibleItemPositionSuggestion = layoutManager.findLastCompletelyVisibleItemPosition();
                if ((banerList.size() % 10) == 0 && findLastVisibleItemPositionSuggestion<banerList.size())
                    callGetAllBrand(next);

            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListenerSuggestion);
    }
}
