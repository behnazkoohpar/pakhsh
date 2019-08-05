package com.koohpar.dstrbt.ui.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.model.api.CategoryResponse;
import com.koohpar.dstrbt.databinding.ActivityCategoryBinding;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.ui.categoryStuff.CategoryStuffActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class CategoryActivity extends BaseActivity<ActivityCategoryBinding, CategoryViewModel> implements CategoryNavigator, AppConstants {

    @Inject
    CategoryViewModel mCategoryViewModel;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    ActivityCategoryBinding mActivityCategoryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityCategoryBinding = getViewDataBinding();
        mCategoryViewModel.setNavigator(this);
        mCategoryViewModel.setActivity(CategoryActivity.this);

        callGetAllCategory();

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, CategoryActivity.class);
        return intent;
    }

    @Override
    public CategoryViewModel getViewModel() {
        return mCategoryViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_category;
    }

    private void callGetAllCategory() {
        try {
            HashMap<String, String> map = new HashMap<>();
            if (LOGTRUE)
                Log.d("mPARAMS Category :::::::: ", map.toString());
            mCategoryViewModel.getAllCategory(iCallApi, this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void setCategory(List<CategoryResponse> categoryResponses) {
        HashMap<String, List<String>> ParentItem = new HashMap<String, List<String>>();
        List<CategoryResponse> parents = new ArrayList<>();

        //select parent name
        for (int i = 0; i < categoryResponses.size(); i++) {
            if (categoryResponses.get(i).getParentID() == null) {
                parents.add(categoryResponses.get(i));
                List<String> childs2 = new ArrayList<>();
                for (int k = 0; k < categoryResponses.size(); k++) {
                    if (categoryResponses.get(k).getParentID() != null && categoryResponses.get(k).getParentID().equalsIgnoreCase(categoryResponses.get(i).getID()))
                        childs2.add(categoryResponses.get(k).getName() + "/" + categoryResponses.get(k).getID());
                }
                ParentItem.put(categoryResponses.get(i).getName(), childs2);
            }
        }
        expandableListDetail = ParentItem;
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new ExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String nama[] = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).split("/");
                Intent intent = CategoryStuffActivity.getStartIntent(CategoryActivity.this, nama[1],null,null);
                startActivity(intent);
                return false;
            }
        });
    }
}
