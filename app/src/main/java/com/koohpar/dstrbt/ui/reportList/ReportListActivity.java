package com.koohpar.dstrbt.ui.reportList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.model.api.ReportListResponse;
import com.koohpar.dstrbt.databinding.ActivityReportListBinding;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.ui.detailReportList.DetailReportListActivity;
import com.koohpar.dstrbt.ui.main.MainActivity;
import com.koohpar.dstrbt.ui.profile.ProfileActivity;
import com.koohpar.dstrbt.ui.profile.ProfileViewModel;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class ReportListActivity extends BaseActivity<ActivityReportListBinding, ReportListViewModel> implements ReportListNavigator, AppConstants {

    @Inject
    ReportListViewModel mReportListViewModel;

    @Inject
    ReportListAdapter reportListAdapter;

    ActivityReportListBinding mActivityReportListBinding;
    private LinearLayoutManager llm = new LinearLayoutManager(ReportListActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityReportListBinding = getViewDataBinding();
        mReportListViewModel.setNavigator(this);
        mReportListViewModel.setActivity(ReportListActivity.this);
        initView();
        getReportList();
    }

    private void getReportList() {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_USER_ID, mReportListViewModel.getDataManager().getUserId());
            if (LOGTRUE)
                Log.d("mPARAMS ::::: ", map.toString());
            mReportListViewModel.getReportList(this, iCallApi, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void setReportList(List<ReportListResponse> reportList) {
        reportListAdapter.addItems(reportList);
    }

    @Override
    public ReportListViewModel getViewModel() {
        return mReportListViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_report_list;
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ReportListActivity.class);
        return intent;
    }

    private void initView() {
        mActivityReportListBinding.reportList.setLayoutManager(llm);
        mActivityReportListBinding.reportList.setItemAnimator(new DefaultItemAnimator());
        mActivityReportListBinding.reportList.setAdapter(reportListAdapter);
        mActivityReportListBinding.reportList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = llm.getChildCount();
                    int totalItemCount = llm.getItemCount();
                    int pastVisibleItems = llm.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        onLoadMore();
                    }
                }
            }
        });
        reportListAdapter.setListener(new ReportListAdapter.OnNewsClickListener() {
            @Override
            public void onItemClickListener(ReportListResponse listResponse) {
                openReportDetailsActivity(listResponse);
            }
        });
    }

    private void onLoadMore() {

    }

    private void openReportDetailsActivity(ReportListResponse listResponse) {
        DetailReportListActivity.detailReportList = listResponse;
        Intent intent = new Intent(DetailReportListActivity.getStartIntent(this));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(MainActivity.getStartIntent(this));
        startActivity(a);
    }
}
