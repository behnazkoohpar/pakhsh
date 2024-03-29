package com.koohpar.dstrbt.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.google.android.material.navigation.NavigationView;
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
import com.koohpar.dstrbt.ui.notifyInventory.ListNotifyInventoryActivity;
import com.koohpar.dstrbt.ui.profile.ProfileActivity;
import com.koohpar.dstrbt.ui.reportList.ReportListActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;
import com.koohpar.dstrbt.utils.CustomTypefaceSpan;
import com.koohpar.dstrbt.utils.EndlessRecyclerViewScrollListener;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.koohpar.dstrbt.ui.splash.SplashActivity.banerList;

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
    private ArrayList<SpecialOfferResponse> suggestionList = new ArrayList<SpecialOfferResponse>();
    private SuggestionAdapter mAdapter;
    private EditText search;
    private int i;
    private ImageView image1, image2, image3, image4, image5;
    private TextView numberShopping;
    private List<SpecialOfferResponse> specialOfferList = new ArrayList<>();
    private EndlessRecyclerViewScrollListener scrollListenerSuggestion;

    private int firstVisiblePositionSuggestion;
    private int findFirstCompletelyVisibleItemPositionSuggestion;
    private int findLastVisibleItemPositionSuggestion;
    private int findLastCompletelyVisibleItemPositionSuggestion;
    private int pageNumSuggestion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);
        mMainViewModel.setActivity(MainActivity.this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();
        setBanner();
        setUpNavDrawer();
        getListShopping();
        getAllSpecialOffer(pageNumSuggestion,10);
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

            menuItem = (ImageView) findViewById(R.id.menu);
            search = (EditText) findViewById(R.id.search);
            numberShopping = (TextView) findViewById(R.id.numberShopping);
            shoppingItem = (ImageView) findViewById(R.id.shopping);
            menuItem.setOnClickListener(this);
            shoppingItem.setOnClickListener(this);
            search.setOnClickListener(this);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
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
        if (id == R.id.notifyInventory) {
            Intent intent = ListNotifyInventoryActivity.getStartIntent(this);
            startActivity(intent);
        }
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

    private void getAllSpecialOffer(int offset, int next) {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_OFFSER, String.valueOf(offset));
            map.put(REQUEST_KEY_NEXT, String.valueOf(next));
            if (LOGTRUE)
                Log.d("mPARAMS :::::::: ", map.toString());
            mMainViewModel.getAllSpecialOffer(iCallApi, this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void setSpecialOffer(List<SpecialOfferResponse> specialOfferResponses) {
        buildRecycleView(specialOfferResponses);
    }

    private void buildRecycleView(final List<SpecialOfferResponse> specialOfferResponses) {
        recyclerViewSuggestion = (RecyclerView) findViewById(R.id.suggestion_list);
        layoutManagerSuggestion = new LinearLayoutManager(this);
        recyclerViewSuggestion.setLayoutManager(layoutManagerSuggestion);

        specialOfferList = specialOfferResponses;
        mAdapter = new SuggestionAdapter(specialOfferResponses);
        recyclerViewSuggestion.setAdapter(mAdapter);
        layoutManagerSuggestion.scrollToPositionWithOffset(firstVisiblePositionSuggestion, pageNumSuggestion);

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

            @Override
            public void onNotifClick(int position) {
                callSetNotif(specialOfferResponses.get(position));
            }
        });

        scrollListenerSuggestion = new EndlessRecyclerViewScrollListener(layoutManagerSuggestion) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                firstVisiblePositionSuggestion = layoutManagerSuggestion.findFirstVisibleItemPosition();
                findFirstCompletelyVisibleItemPositionSuggestion = layoutManagerSuggestion.findFirstCompletelyVisibleItemPosition();
                findLastVisibleItemPositionSuggestion = layoutManagerSuggestion.findLastVisibleItemPosition();
                findLastCompletelyVisibleItemPositionSuggestion = layoutManagerSuggestion.findLastCompletelyVisibleItemPosition();
                if (!(specialOfferList.size()<10)) {
                    getAllSpecialOffer(page , 10);
//                    getAllSpecialOfferImage(page, 10);
                }
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerViewSuggestion.addOnScrollListener(scrollListenerSuggestion);

//        getAllSpecialOfferImage(pageNumSuggestion, 10);
    }
    public void setBanner() {
        recyclerView = (RecyclerView) findViewById(R.id.advertise_list);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new AdvertiseRecycleViewAdapter(banerList));
        mActivityMainBinding.indicator.attachToRecyclerView(recyclerView, 50);
    }

    private void callSetNotif(SpecialOfferResponse specialOfferResponse) {
        try {
            PersianCalendar persianCalendar = new PersianCalendar();
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_STUFF_BRAND_ID, specialOfferResponse.getID());
            map.put(REQUEST_KEY_USER_ID, mMainViewModel.getDataManager().getUserId());
            map.put(REQUEST_KEY_CREATE_REQUEST, persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay());
            if (LOGTRUE)
                Log.d("mPARAMS Category :::::::: ", map.toString());
            mMainViewModel.setNotif(iCallApi, this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
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
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
