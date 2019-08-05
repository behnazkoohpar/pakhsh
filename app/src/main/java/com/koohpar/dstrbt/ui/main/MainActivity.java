package com.koohpar.dstrbt.ui.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.model.api.BannerResponse;
import com.koohpar.dstrbt.data.model.api.SpecialOfferResponse;
import com.koohpar.dstrbt.data.model.api.database.StuffSelected;
import com.koohpar.dstrbt.databinding.ActivityMainBinding;
import com.koohpar.dstrbt.ui.about.AboutUsActivity;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.ui.brand.BrandActivity;
import com.koohpar.dstrbt.ui.category.CategoryActivity;
import com.koohpar.dstrbt.ui.categoryStuff.CategoryStuffActivity;
import com.koohpar.dstrbt.ui.listSelectedStuff.ListSelectedStuffActivity;
import com.koohpar.dstrbt.ui.login.LoginActivity;
import com.koohpar.dstrbt.ui.profile.ProfileActivity;
import com.koohpar.dstrbt.ui.reportList.ReportListActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;
import com.koohpar.dstrbt.utils.CustomTypefaceSpan;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, AppConstants, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    MainViewModel mMainViewModel;

    ActivityMainBinding mActivityMainBinding;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private TextView name, version;
    private CircleImageView ivUserImage;
    private Typeface font;
    private String versionName;
    private ImageView menuItem, logoItem, shoppingItem;
    private RecyclerView recyclerView, recyclerViewSuggestion;
    private LinearLayoutManager layoutManager, layoutManagerSuggestion;
    List<BannerResponse> banerList = new ArrayList<BannerResponse>();
    private ArrayList<SpecialOfferResponse> suggestionList = new ArrayList<SpecialOfferResponse>();
    private SuggestionAdapter mAdapter;
    private EditText search;
    private int i;
    private ImageView image1, image2, image3, image4, image5;
    private TextView numberShopping;
    private List<SpecialOfferResponse> specialOfferList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);
        mMainViewModel.setActivity(MainActivity.this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();
        setUpNavDrawer();
        getListShopping();
    }

    private void getListShopping() {
        int listsize = 0;
        if (StuffSelected.findAll() != null)
            listsize = StuffSelected.findAll().size();
        if (listsize > 0) {
            numberShopping.setVisibility(View.VISIBLE);
            numberShopping.setText(String.valueOf(listsize));
        }

    }

    private void initView() {
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        recyclerView = (RecyclerView) findViewById(R.id.advertise_list);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        menuItem = (ImageView) findViewById(R.id.menu);
        logoItem = (ImageView) findViewById(R.id.logo);
        search = (EditText) findViewById(R.id.search);
        numberShopping = (TextView) findViewById(R.id.numberShopping);
        shoppingItem = (ImageView) findViewById(R.id.shopping);
        menuItem.setOnClickListener(this);
        shoppingItem.setOnClickListener(this);
        search.setOnClickListener(this);
    }

    @Override
    public MainViewModel getViewModel() {
        return mMainViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    private void setUpNavDrawer() {
        navigationView = (NavigationView) findViewById(R.id.nv);
        drawerLayout = (DrawerLayout) findViewById(R.id.dl);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, null, 0, 0);
        drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        View header = navigationView.getHeaderView(0);
        name = (TextView) header.findViewById(R.id.nameUser);
        name.setText(getString(R.string.name));
        version = (TextView) header.findViewById(R.id.version);
        version.setText(versionName);
        ivUserImage = (CircleImageView) header.findViewById(R.id.image);
        name.setText((mMainViewModel.getDataManager().getFirstName() + " " + mMainViewModel.getDataManager().getLastName()).toString());
        setNavigationHeaderUserImage(mMainViewModel.getDataManager().getProfilePicture());
        //set font for menu
        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            applyFontToMenuItem(mi);
        }
    }

    public void setNavigationHeaderUserImage(Bitmap bitmap) {
        ivUserImage.setImageBitmap(bitmap);
    }

    public void setNavigationHeaderUserImage(String image) {
        if (image != null && !image.isEmpty() && !image.equalsIgnoreCase("null")) {
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ivUserImage.setImageBitmap(decodedByte);
        }
    }

    private void applyFontToMenuItem(MenuItem mi) {
        font = Typeface.createFromAsset(getAssets(), "fonts/iran_sans.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu:
                name.setText((mMainViewModel.getDataManager().getFirstName() + " " + mMainViewModel.getDataManager().getLastName()).toString());
                setNavigationHeaderUserImage(mMainViewModel.getDataManager().getProfilePicture());
                drawerLayout.openDrawer(Gravity.RIGHT);
                break;
            case R.id.shopping:
                Intent intent = ListSelectedStuffActivity.getStartIntent(this);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.shopping) {
            Intent intent = ListSelectedStuffActivity.getStartIntent(this);
            startActivity(intent);
        }
        if (id == R.id.report) {
            Intent intent = ReportListActivity.getStartIntent(this);
            startActivity(intent);
        }
        if (id == R.id.profile) {
            Intent intent = ProfileActivity.getStartIntent(this);
            startActivity(intent);
        }
        if (id == R.id.category) {
            Intent intent = CategoryActivity.getStartIntent(this);
            startActivity(intent);
        }
        if (id == R.id.brand) {
            Intent intent = BrandActivity.getStartIntent(this);
            startActivity(intent);
        }
        if (id == R.id.aboutme) {
            Intent intent = AboutUsActivity.getStartIntent(this);
            startActivity(intent);
        }
        if (id == R.id.exit) {
            CommonUtils.showTwoButtonAlert(MainActivity.this, getString(R.string.eit_ok), getString(R.string.ok), getString(R.string.cancel), new CommonUtils.IL() {
                @Override
                public void onSuccess() {
                    setLogOut();
                }

                @Override
                public void onCancel() {
                }
            });
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dl);
        drawer.closeDrawer(Gravity.RIGHT);
        return true;
    }

    public void setLogOut() {
        mMainViewModel.getDataManager().setUserAsLoggedOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    @Override
    public void openCategoryList() {
        Intent intent = CategoryActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void callSearch() {
        openCategoryStuff();
    }

    private void openCategoryStuff() {
        Intent intent = CategoryStuffActivity.getStartIntent(this, null, null, search.getText().toString());
        startActivity(intent);
    }

    private void getAllBanerList() {
        try {
            HashMap<String, String> map = new HashMap<>();
            if (LOGTRUE)
                Log.d("mPARAMS BanerList :::::::: ", map.toString());
            mMainViewModel.getBanerList(iCallApi, this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    private void getAllSpecialOffer() {
        try {
            HashMap<String, String> map = new HashMap<>();
            if (LOGTRUE)
                Log.d("mPARAMS SpecialOffer :::::::: ", map.toString());
            mMainViewModel.getAllSpecialOffer(iCallApi, this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void setBanner(List<BannerResponse> bannerResponses) {
        banerList = null;
        banerList = new ArrayList<BannerResponse>();
        banerList.addAll(bannerResponses);
        recyclerView.setAdapter(new AdvertiseRecycleViewAdapter(banerList));

        getAllSpecialOffer();
    }

    @Override
    public void setSpecialOffer(List<SpecialOfferResponse> specialOfferResponses) {
        buildRecycleView(specialOfferResponses);
    }

    private void buildRecycleView(final List<SpecialOfferResponse> specialOfferResponses) {

        recyclerViewSuggestion = (RecyclerView) findViewById(R.id.suggestion_list);
        layoutManagerSuggestion = new LinearLayoutManager(this);
        recyclerViewSuggestion.setLayoutManager(layoutManagerSuggestion);
        mAdapter = new SuggestionAdapter(specialOfferResponses);
        specialOfferList = specialOfferResponses;
        recyclerViewSuggestion.setAdapter(mAdapter);


        mAdapter.setOnitemclickListener(new SuggestionAdapter.OnItemClickListener() {
            @Override
            public void onIncreaseClick(int position) {
                try {
                    StuffSelected.setToDB(specialOfferList.get(position));
                    specialOfferList.get(position).setVisibility(View.GONE);
                    recyclerViewSuggestion.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.shopping).setVisibility(View.GONE);
                    int listsize = 0;
                    if (StuffSelected.findAll() != null)
                        listsize = StuffSelected.findAll().size();
                    if (listsize > 0) {
                        numberShopping.setVisibility(View.VISIBLE);
                        numberShopping.setText(String.valueOf(listsize));
                    }
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
        suggestionList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onResume() {
        super.onResume();
        int listsize = 0;
        if (StuffSelected.findAll() != null)
            listsize = StuffSelected.findAll().size();
        if (listsize > 0) {
            numberShopping.setVisibility(View.VISIBLE);
            numberShopping.setText(String.valueOf(listsize));
        } else {
            numberShopping.setVisibility(View.GONE);
            numberShopping.setText(String.valueOf(0));
        }
        getAllBanerList();
//        getAllSpecialOffer();
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
