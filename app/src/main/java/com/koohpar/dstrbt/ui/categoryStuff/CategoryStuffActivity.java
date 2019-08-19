package com.koohpar.dstrbt.ui.categoryStuff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.model.api.CategoryStuffResponse;
import com.koohpar.dstrbt.data.model.api.database.StuffSelected;
import com.koohpar.dstrbt.databinding.ActivityCategoryStuffBinding;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;
import com.koohpar.dstrbt.utils.EndlessRecyclerViewScrollListener;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class CategoryStuffActivity extends BaseActivity<ActivityCategoryStuffBinding, CategoryStuffViewModel> implements CategoryStuffNavigator, AppConstants {

    private static String CategoryId;
    public static String BrandId;
    private static String Search;

    @Inject
    CategoryStuffViewModel mCategoryStuffViewModel;

    ActivityCategoryStuffBinding mActivityCategoryStuffBinding;
    private RecyclerView recyclerViewCategoryStuff;
    private LinearLayoutManager layoutManagerCategoryStuff;
    private CategoryStuffAdapter mAdapter;
    private List<CategoryStuffResponse> categoryStuffList = new ArrayList<>();
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
        mActivityCategoryStuffBinding = getViewDataBinding();
        mCategoryStuffViewModel.setNavigator(this);
        mCategoryStuffViewModel.setActivity(CategoryStuffActivity.this);
        recyclerViewCategoryStuff = (RecyclerView) findViewById(R.id.category_stuff_list);
        layoutManagerCategoryStuff = new LinearLayoutManager(this);
        recyclerViewCategoryStuff.setLayoutManager(layoutManagerCategoryStuff);
        mAdapter = new CategoryStuffAdapter(categoryStuffList);
        recyclerViewCategoryStuff.setAdapter(mAdapter);

        if (CategoryId != null)
            callGetStuffListFromCategory(next);
        if (BrandId != null)
            callGetStuffListFromBrand();
        if (Search != null)
            callCategoryStuffBySearch();
    }

    public static Intent getStartIntent(Context context, String categoryId, String brandId, String search) {
        BrandId = brandId;
        CategoryId = categoryId;
        Search = search;
        Intent intent = new Intent(context, CategoryStuffActivity.class);
        return intent;
    }

    @Override
    public CategoryStuffViewModel getViewModel() {
        return mCategoryStuffViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_category_stuff;
    }

    private void callCategoryStuffBySearch() {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_SEARCH, "%" + Search + "%");
            if (LOGTRUE)
                Log.d("mPARAMS Search :::::::: ", map.toString());
            mCategoryStuffViewModel.getSearchKeyword(iCallApi, this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    private void callGetStuffListFromCategory(int next) {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_OFFSER, String.valueOf(pageNum));
            map.put(REQUEST_KEY_NEXT, String.valueOf(next));
            map.put(REQUEST_KEY_CATEGORY_ID, CategoryId);
            if (LOGTRUE)
                Log.d("mPARAMS Category :::::::: ", map.toString());
            mCategoryStuffViewModel.getAllCategoryStuff(iCallApi, this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    private void callGetStuffListFromBrand() {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_OFFSER, String.valueOf(pageNum));
            map.put(REQUEST_KEY_NEXT, String.valueOf(next));
            map.put(REQUEST_KEY_BRAND_ID, BrandId);
            if (LOGTRUE)
                Log.d("mPARAMS Category :::::::: ", map.toString());
            mCategoryStuffViewModel.getAllCategoryStuffByBrand(iCallApi, this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void setCategoryStuff(List<CategoryStuffResponse> categoryStuffResponses) {
        buildRecycleView(categoryStuffResponses);
    }

    private void buildRecycleView(final List<CategoryStuffResponse> categoryStuffResponses) {

        categoryStuffList.addAll(categoryStuffResponses);
        mAdapter.notifyDataSetChanged();
        layoutManagerCategoryStuff.scrollToPositionWithOffset(firstVisiblePositionSuggestion, pageNum);
        pageNum = pageNum + next;

        mAdapter.setOnitemclickListener(new CategoryStuffAdapter.OnItemClickListener() {
            @Override
            public void onIncreaseClick(int position) {
                try {
                    StuffSelected.setToDB(categoryStuffList.get(position));
                    categoryStuffList.get(position).setVisibility(View.GONE);
                    recyclerViewCategoryStuff.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.shopping).setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mAdapter.notifyItemChanged(position);
            }

            @Override
            public void onNotifClick(int position) {
                callSetNotif(categoryStuffResponses.get(position));
            }
        });

        scrollListenerSuggestion = new EndlessRecyclerViewScrollListener(layoutManagerCategoryStuff) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                firstVisiblePositionSuggestion = layoutManagerCategoryStuff.findFirstVisibleItemPosition();
                findFirstCompletelyVisibleItemPositionSuggestion = layoutManagerCategoryStuff.findFirstCompletelyVisibleItemPosition();
                findLastVisibleItemPositionSuggestion = layoutManagerCategoryStuff.findLastVisibleItemPosition();
                findLastCompletelyVisibleItemPositionSuggestion = layoutManagerCategoryStuff.findLastCompletelyVisibleItemPosition();
                if ((categoryStuffList.size() % 10) == 0 && findLastVisibleItemPositionSuggestion<categoryStuffList.size())
                    callGetStuffListFromCategory(next);

            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerViewCategoryStuff.addOnScrollListener(scrollListenerSuggestion);

    }

    private void callSetNotif(CategoryStuffResponse categoryStuffResponse) {
        try {
            PersianCalendar persianCalendar = new PersianCalendar();
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_STUFF_BRAND_ID, categoryStuffResponse.getID());
            map.put(REQUEST_KEY_USER_ID, mCategoryStuffViewModel.getDataManager().getUserId());
            map.put(REQUEST_KEY_CREATE_REQUEST, persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay());
            if (LOGTRUE)
                Log.d("mPARAMS Category :::::::: ", map.toString());
            mCategoryStuffViewModel.setNotif(iCallApi, this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    private void removeItem(int position) {
//        suggestionList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

}
