package com.koohpar.dstrbt.ui.categoryStuff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.model.api.CategoryStuffResponse;
import com.koohpar.dstrbt.data.model.api.database.StuffSelected;
import com.koohpar.dstrbt.databinding.ActivityCategoryStuffBinding;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.ui.main.MainActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import org.json.JSONException;

import java.io.IOException;
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
    private List<CategoryStuffResponse> categoryStuffList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityCategoryStuffBinding = getViewDataBinding();
        mCategoryStuffViewModel.setNavigator(this);
        mCategoryStuffViewModel.setActivity(CategoryStuffActivity.this);
        if (CategoryId != null)
            callGetStuffListFromCategory();
        if(BrandId != null)
            callGetStuffListFromBrand();
        if(Search != null)
            callCategoryStuffBySearch();
    }

    public static Intent getStartIntent(Context context, String categoryId,String brandId,String search) {
        BrandId =brandId;
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

    private void callCategoryStuffBySearch(){
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_SEARCH,"%"+Search+"%");
            if (LOGTRUE)
                Log.d("mPARAMS Search :::::::: ", map.toString());
            mCategoryStuffViewModel.getSearchKeyword(iCallApi, this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    private void callGetStuffListFromCategory() {
        try {
            HashMap<String, String> map = new HashMap<>();
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

    private void buildRecycleView(List<CategoryStuffResponse> categoryStuffResponses) {

        recyclerViewCategoryStuff = (RecyclerView) findViewById(R.id.category_stuff_list);
        layoutManagerCategoryStuff = new LinearLayoutManager(this);
        recyclerViewCategoryStuff.setLayoutManager(layoutManagerCategoryStuff);
        mAdapter = new CategoryStuffAdapter(categoryStuffResponses);
        categoryStuffList =categoryStuffResponses;
        recyclerViewCategoryStuff.setAdapter(mAdapter);

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
        });
    }

    private void removeItem(int position) {
//        suggestionList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

}
