package com.koohpar.dstrbt.ui.listSelectedStuff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.model.api.database.StuffSelected;
import com.koohpar.dstrbt.databinding.ActivityListSelectedStuffBinding;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.ui.profile.ProfileActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class ListSelectedStuffActivity extends BaseActivity<ActivityListSelectedStuffBinding, ListSelectedStuffViewModel> implements ListSelectedStuffNavigator, AppConstants {

    @Inject
    ListSelectedStuffViewModel mListSelectedStuffViewModel;

    ActivityListSelectedStuffBinding mActivityListSelectedStuffBinding;
    private RecyclerView recyclerViewListSelectedStuff;
    private LinearLayoutManager layoutManagerListSelectedStuff;
    private ListSelectedStuffAdapter mAdapter;
    private List<StuffSelected> stuffSelecteds;
    private TextView sum;
    int sumP = 0;
    NumberFormat numberFormat = NumberFormat.getNumberInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityListSelectedStuffBinding = getViewDataBinding();
        mListSelectedStuffViewModel.setNavigator(this);
        mListSelectedStuffViewModel.setActivity(ListSelectedStuffActivity.this);
        stuffSelecteds = StuffSelected.findAll();
        sum = (TextView) findViewById(R.id.sum);
        recyclerViewListSelectedStuff = (RecyclerView) findViewById(R.id.category_stuff_list_selected);
        layoutManagerListSelectedStuff = new LinearLayoutManager(this);
        recyclerViewListSelectedStuff.setLayoutManager(layoutManagerListSelectedStuff);
        mAdapter = new ListSelectedStuffAdapter(stuffSelecteds);
        recyclerViewListSelectedStuff.setAdapter(mAdapter);
        if (stuffSelecteds != null)
            for (int i = 0; i < stuffSelecteds.size(); i++) {
                sumP = sumP + Integer.parseInt(stuffSelecteds.get(i).getSumPrice());
            }
        sum.setText(numberFormat.format(sumP));

        mAdapter.setOnitemclickListener(new ListSelectedStuffAdapter.OnItemClickListener() {
            @Override
            public void onIncreaseClick(int position) {
                int numberIncrease = StuffSelected.updateByGUID(stuffSelecteds.get(position));
                stuffSelecteds.get(position).setNumberSelected(numberIncrease);
                if (stuffSelecteds.get(position).getOffer() != null &&
                        Integer.parseInt(stuffSelecteds.get(position).getOffer()) > 0)
                    stuffSelecteds.get(position).setSumPrice(String.valueOf((Integer.parseInt(stuffSelecteds.get(position).getPrice()) * stuffSelecteds.get(position).getNumberSelected()) -
                            ((Integer.parseInt(stuffSelecteds.get(position).getPrice()) * stuffSelecteds.get(position).getNumberSelected()) / 100) * Integer.parseInt(stuffSelecteds.get(position).getOffer())));
                else
                    stuffSelecteds.get(position).setSumPrice(String.valueOf(Integer.parseInt(stuffSelecteds.get(position).getPrice()) * stuffSelecteds.get(position).getNumberSelected()));
                mAdapter.notifyItemChanged(position);
                sumP = 0;
                for (int i = 0; i < stuffSelecteds.size(); i++) {

                    if (stuffSelecteds.get(i).getOffer() != null &&
                            Integer.parseInt(stuffSelecteds.get(i).getOffer()) > 0)
                        sumP = sumP + ((Integer.parseInt(stuffSelecteds.get(i).getPrice()) * stuffSelecteds.get(i).getNumberSelected()) -
                                ((Integer.parseInt(stuffSelecteds.get(i).getPrice()) * stuffSelecteds.get(i).getNumberSelected()) / 100) * Integer.parseInt(stuffSelecteds.get(i).getOffer()));
                    else
                        sumP = sumP + Integer.parseInt(stuffSelecteds.get(i).getPrice()) * stuffSelecteds.get(i).getNumberSelected();
                }
                sum.setText(numberFormat.format(sumP));
            }

            @Override
            public void onDecreaseClick(int position) {
                int numberIncrease = StuffSelected.updateDecreaseByGUID(stuffSelecteds.get(position));
                if (numberIncrease > 0) {
                    stuffSelecteds.get(position).setNumberSelected(numberIncrease);
                    if (stuffSelecteds.get(position).getOffer() != null &&
                            Integer.parseInt(stuffSelecteds.get(position).getOffer()) > 0)
                        stuffSelecteds.get(position).setSumPrice(String.valueOf((Integer.parseInt(stuffSelecteds.get(position).getPrice()) * stuffSelecteds.get(position).getNumberSelected()) -
                                ((Integer.parseInt(stuffSelecteds.get(position).getPrice()) * stuffSelecteds.get(position).getNumberSelected()) / 100) * Integer.parseInt(stuffSelecteds.get(position).getOffer())));
                    else
                        stuffSelecteds.get(position).setSumPrice(String.valueOf(Integer.parseInt(stuffSelecteds.get(position).getPrice()) * stuffSelecteds.get(position).getNumberSelected()));

                    mAdapter.notifyItemChanged(position);
                } else {
                    stuffSelecteds.remove(position);
                    mAdapter.notifyItemRemoved(position);
                }
                sumP = 0;
                for (int i = 0; i < stuffSelecteds.size(); i++) {
                    sumP = sumP + Integer.parseInt(stuffSelecteds.get(i).getPrice()) * stuffSelecteds.get(i).getNumberSelected();
                }
                sum.setText(numberFormat.format(sumP));
            }
        });
    }

    private void removeItem(int position) {
//        suggestionList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    @Override
    public ListSelectedStuffViewModel getViewModel() {
        return mListSelectedStuffViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_list_selected_stuff;
    }


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ListSelectedStuffActivity.class);
        return intent;
    }

    @Override
    public void setOrder() {
        try {
            List<StuffSelected> stuffSelected = StuffSelected.findAll();
            if (stuffSelected != null) {
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < stuffSelected.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("item_id", Integer.parseInt(stuffSelected.get(i).getGUID()));
                    jsonObject.put("count", stuffSelected.get(i).getNumberSelected());
                    jsonObject.put("consumer_price", stuffSelected.get(i).getConsumerPrice());
                    jsonObject.put("price", stuffSelected.get(i).getPrice());
                    jsonObject.put("offer", stuffSelected.get(i).getOffer());
                    jsonObject.put("sum_price", stuffSelected.get(i).getSumPrice());
                    jsonArray.put(jsonObject);
                }
                HashMap<String, String> mParams = new HashMap<String, String>();
                mParams.put(REQUEST_KEY_USER_ID, mListSelectedStuffViewModel.getDataManager().getUserId());
                mParams.put(REQUEST_KEY_SUM_PRICE, String.valueOf(sumP));
                mParams.put(REQUEST_KEY_STUFFS_ACCOUNT, String.valueOf(stuffSelected.size()));
                mParams.put(REQUEST_KEY_REQUEST_ITEM, jsonArray.toString());
                if (LOGTRUE)
                    Log.d("setRequest :::::::: ", mParams.toString());
                mListSelectedStuffViewModel.callSetRequest(iCallApi, this, mParams);
            }
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void checkAdderess() {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_MOBILE_NUMBER, mListSelectedStuffViewModel.getDataManager().getUsername());
            if (LOGTRUE)
                Log.d("mPARAMS :::::::: ", map.toString());
            mListSelectedStuffViewModel.ProfileUser(iCallApi, this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public void openProfile() {
        Intent intent = new Intent(ProfileActivity.getStartIntent(this));
        startActivity(intent);
    }

    @Override
    public void setDelete() {
        CommonUtils.showTwoButtonAlert(this, getString(R.string.delete_ok), getString(R.string.pop_up_ok), getString(R.string.cancel), new CommonUtils.IL() {
            @Override
            public void onSuccess() {
                try {
                    StuffSelected.deleteAll();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancel() {

            }
        });

    }
}
