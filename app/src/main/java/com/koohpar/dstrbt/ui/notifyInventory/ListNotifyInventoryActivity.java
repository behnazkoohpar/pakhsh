package com.koohpar.dstrbt.ui.notifyInventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.model.api.CategoryStuffResponse;
import com.koohpar.dstrbt.data.model.api.database.StuffSelected;
import com.koohpar.dstrbt.databinding.ActivityListNotifyInventoryBinding;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;
import com.koohpar.dstrbt.utils.EndlessRecyclerViewScrollListener;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class ListNotifyInventoryActivity extends BaseActivity<ActivityListNotifyInventoryBinding, ListNotifyInventoryViewModel> implements ListNotifyInventoryNavigator, AppConstants {

    @Inject
    ListNotifyInventoryViewModel mListNotifyInventoryViewModel;

    ActivityListNotifyInventoryBinding mActivityListNotifyInventoryBinding;
    private LinearLayoutManager layoutManagerListNotifyInventory;
    private ListNotifyInventoryAdapter mAdapter;
    private List<CategoryStuffResponse> categoryStuffResponseList= new ArrayList<>();
    private EndlessRecyclerViewScrollListener scrollListenerSuggestion;

    private int firstVisiblePositionSuggestion;
    private int findFirstCompletelyVisibleItemPositionSuggestion;
    private int findLastVisibleItemPositionSuggestion;
    private int findLastCompletelyVisibleItemPositionSuggestion;
    private int pageNum = 0;
    private int next = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityListNotifyInventoryBinding = getViewDataBinding();
        mListNotifyInventoryViewModel.setNavigator(this);
        mListNotifyInventoryViewModel.setActivity(ListNotifyInventoryActivity.this);
        layoutManagerListNotifyInventory = new LinearLayoutManager(this);
        mActivityListNotifyInventoryBinding.list.setLayoutManager(layoutManagerListNotifyInventory);
        mAdapter = new ListNotifyInventoryAdapter(categoryStuffResponseList);
        mActivityListNotifyInventoryBinding.list.setAdapter(mAdapter);
        callListNotify(next);

    }

    private void callListNotify( int next) {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_OFFSER, String.valueOf(pageNum));
            map.put(REQUEST_KEY_NEXT, String.valueOf(next));
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
        categoryStuffResponseList.addAll(categoryStuffResponse);
        mAdapter.notifyDataSetChanged();
        layoutManagerListNotifyInventory.scrollToPositionWithOffset(firstVisiblePositionSuggestion, pageNum);
        pageNum = pageNum + next;

        mAdapter.setOnitemclickListener(new ListNotifyInventoryAdapter.OnItemClickListener() {
            @Override
            public void onIncreaseClick(int position) {
                try {
                    StuffSelected.setToDB(categoryStuffResponseList.get(position));
                    categoryStuffResponseList.get(position).setVisibility(View.GONE);
                    mActivityListNotifyInventoryBinding.list.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.shopping).setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mAdapter.notifyItemChanged(position);
            }
        });

        scrollListenerSuggestion = new EndlessRecyclerViewScrollListener(layoutManagerListNotifyInventory) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                firstVisiblePositionSuggestion = layoutManagerListNotifyInventory.findFirstVisibleItemPosition();
                findFirstCompletelyVisibleItemPositionSuggestion = layoutManagerListNotifyInventory.findFirstCompletelyVisibleItemPosition();
                findLastVisibleItemPositionSuggestion = layoutManagerListNotifyInventory.findLastVisibleItemPosition();
                findLastCompletelyVisibleItemPositionSuggestion = layoutManagerListNotifyInventory.findLastCompletelyVisibleItemPosition();
                if ((categoryStuffResponseList.size() % 10) == 0 && findLastVisibleItemPositionSuggestion<categoryStuffResponseList.size())
                    callListNotify(next);

            }
        };
        // Adds the scroll listener to RecyclerView
        mActivityListNotifyInventoryBinding.list.addOnScrollListener(scrollListenerSuggestion);
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
