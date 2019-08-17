package com.koohpar.dstrbt.ui.notifyInventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.model.api.CategoryStuffResponse;
import com.koohpar.dstrbt.databinding.ActivityListNotifyInventoryBinding;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class ListNotifyInventoryActivity extends BaseActivity<ActivityListNotifyInventoryBinding, ListNotifyInventoryViewModel> implements ListNotifyInventoryNavigator, AppConstants {

    @Inject
    ListNotifyInventoryViewModel mListNotifyInventoryViewModel;

    ActivityListNotifyInventoryBinding mActivityListNotifyInventoryBinding;
    private LinearLayoutManager layoutManagerListNotifyInventory;
    private ListNotifyInventoryAdapter mAdapter;
    private List<CategoryStuffResponse> categoryStuffResponseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityListNotifyInventoryBinding = getViewDataBinding();
        mListNotifyInventoryViewModel.setNavigator(this);
        mListNotifyInventoryViewModel.setActivity(ListNotifyInventoryActivity.this);
        callListNotify();

    }

    private void callListNotify() {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_USER_ID, mListNotifyInventoryViewModel.getDataManager().getUserId());
            if (LOGTRUE)
                Log.d("mPARAMS ::::: ", map.toString());
            mListNotifyInventoryViewModel.getNitifyInventoryList(this, iCallApi, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }

    }

    public void setListNotify(List<CategoryStuffResponse> categoryStuffResponse) {
        categoryStuffResponseList = categoryStuffResponse;
        layoutManagerListNotifyInventory = new LinearLayoutManager(this);
        mActivityListNotifyInventoryBinding.list.setLayoutManager(layoutManagerListNotifyInventory);
        mAdapter = new ListNotifyInventoryAdapter(categoryStuffResponse);
        mActivityListNotifyInventoryBinding.list.setAdapter(mAdapter);
        mAdapter.setOnitemclickListener(new ListNotifyInventoryAdapter.OnItemClickListener() {
            @Override
            public void onIncreaseClick(int position) {

            }

            @Override
            public void onNotifClick(int position) {

            }
        });
    }

    private void removeItem(int position) {
//        suggestionList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    @Override
    public ListNotifyInventoryViewModel getViewModel() {
        return mListNotifyInventoryViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_list_notify_inventory;
    }


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ListNotifyInventoryActivity.class);
        return intent;
    }
}
