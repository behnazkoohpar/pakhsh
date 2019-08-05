package com.koohpar.dstrbt.ui.detailReportList;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.model.api.DetailReportListResponse;
import com.koohpar.dstrbt.data.model.api.ReportListResponse;
import com.koohpar.dstrbt.databinding.ActivityDetailReportListBinding;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.ui.reportList.ReportListActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;
import com.koohpar.dstrbt.utils.fdate.DateUtil;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class DetailReportListActivity extends BaseActivity<ActivityDetailReportListBinding, DetailReportListViewModel> implements DetailReportListNavigator, AppConstants {

    public static ReportListResponse detailReportList;
    NumberFormat numberFormat = NumberFormat.getNumberInstance();
    @Inject
    DetailReportListViewModel mDetailReportListViewModel;
    @Inject
    DetailReportListAdapter detailReportListAdapter;

    ActivityDetailReportListBinding mActivityDetailReportListBinding;
    private LinearLayoutManager llm = new LinearLayoutManager(DetailReportListActivity.this);
    private List<DetailReportListResponse> detailRequestResponses;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDetailReportListBinding = getViewDataBinding();
        mDetailReportListViewModel.setNavigator(this);
        mDetailReportListViewModel.setActivity(DetailReportListActivity.this);
        initView();
        getDetailReportList();

        mActivityDetailReportListBinding.numberFactor.setText(detailReportList.getID());
        mActivityDetailReportListBinding.sumFactor.setText(numberFormat.format(Integer.parseInt(detailReportList.getSumPrice())));

        int year = Integer.parseInt(detailReportList.getRequestDateTime().substring(0, 4));
        int month = Integer.parseInt(detailReportList.getRequestDateTime().substring(5, 7));
        int day = Integer.parseInt(detailReportList.getRequestDateTime().substring(8, 10));
        mActivityDetailReportListBinding.dateFactor.setText(DateUtil.changeMiladiToFarsi(year + "/" + month + "/" + day));
        if (!detailReportList.getAccepted().equalsIgnoreCase("false"))
            mActivityDetailReportListBinding.deleteRequest.setVisibility(View.GONE);

        if (detailReportList.getSended().equalsIgnoreCase("true") &&
                detailReportList.getSendedDateTime() != null) {
            mActivityDetailReportListBinding.stateFactor.setText("ارسال شد");
            mActivityDetailReportListBinding.threeL.setVisibility(View.VISIBLE);
            mActivityDetailReportListBinding.twoL.setVisibility(View.GONE);
            mActivityDetailReportListBinding.firstL.setVisibility(View.GONE);
        } else if (detailReportList.getAccepted().equalsIgnoreCase("true") &&
                detailReportList.getAcceptedDatetime() != null) {
            mActivityDetailReportListBinding.stateFactor.setText("تائید شد");
            mActivityDetailReportListBinding.threeL.setVisibility(View.GONE);
            mActivityDetailReportListBinding.twoL.setVisibility(View.VISIBLE);
            mActivityDetailReportListBinding.firstL.setVisibility(View.GONE);
        } else if (detailReportList.getAccepted().equalsIgnoreCase("false")) {
            if (detailReportList.getAcceptedDatetime() != null) {
                mActivityDetailReportListBinding.stateFactor.setText("رد درخواست");
                mActivityDetailReportListBinding.threeL.setVisibility(View.GONE);
                mActivityDetailReportListBinding.twoL.setVisibility(View.GONE);
                mActivityDetailReportListBinding.firstL.setVisibility(View.GONE);
                mActivityDetailReportListBinding.textState.setVisibility(View.GONE);
            } else {
                mActivityDetailReportListBinding.stateFactor.setText("در انتظار تائید");
                mActivityDetailReportListBinding.threeL.setVisibility(View.GONE);
                mActivityDetailReportListBinding.twoL.setVisibility(View.GONE);
                mActivityDetailReportListBinding.firstL.setVisibility(View.VISIBLE);
            }
        }
    }

    private void getDetailReportList() {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_REQUEST_ID, detailReportList.getID());
            if (LOGTRUE)
                Log.d("mPARAMS ::::: ", map.toString());
            mDetailReportListViewModel.getDetailReportList(this, iCallApi, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    @Override
    public DetailReportListViewModel getViewModel() {
        return mDetailReportListViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail_report_list;
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, DetailReportListActivity.class);
        return intent;
    }

    private void initView() {
        mActivityDetailReportListBinding.detailReportList.setLayoutManager(llm);
        mActivityDetailReportListBinding.detailReportList.setItemAnimator(new DefaultItemAnimator());
        mActivityDetailReportListBinding.detailReportList.setAdapter(detailReportListAdapter);
        mActivityDetailReportListBinding.detailReportList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        detailReportListAdapter.setListener(new DetailReportListAdapter.OnDetailResponseClickListener() {

            @Override
            public void onDeleteListener(DetailReportListResponse item) {
                callDeleteItemRequest(item);
            }

            @Override
            public void onEditListener(DetailReportListResponse item, int position) {
                callEditItem(item, position);
            }
        });
    }

    private void onLoadMore() {

    }

    @Override
    public void setDetailReportList(List<DetailReportListResponse> detailRequestResponses) {
        this.detailRequestResponses = detailRequestResponses;
        detailReportListAdapter.addItems(detailRequestResponses);
        setForm();
        if (detailRequestResponses.size() == 0) {
            CommonUtils.showSingleButtonAlert(DetailReportListActivity.this, getString(R.string.text_attention), getString(R.string.data_is_null), null, new CommonUtils.IL() {
                @Override
                public void onSuccess() {
                    callDelete();
                }

                @Override
                public void onCancel() {
                    callDelete();
                }
            });

        }
    }

    @Override
    public void callDeleteRequest() {
        CommonUtils.showTwoButtonAlert(this, "آیا از حذف درخواست اطمینان دارید؟", getString(R.string.btn_ok), getString(R.string.cancel), new CommonUtils.IL() {
            @Override
            public void onSuccess() {
                callDelete();
            }

            @Override
            public void onCancel() {
            }
        });
    }

    private void callDelete() {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_USER_ID, mDetailReportListViewModel.getDataManager().getUserId());
            map.put(REQUEST_KEY_REQUEST_ID, detailReportList.getID());
            if (LOGTRUE)
                Log.d("mPARAMS ::::: ", map.toString());
            mDetailReportListViewModel.deleteDetailReportList(DetailReportListActivity.this, iCallApi, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(DetailReportListActivity.this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    private void callDeleteItemRequest(final DetailReportListResponse item) {
        CommonUtils.showTwoButtonAlert(this, "آیا از حذف کالا اطمینان دارید؟", getString(R.string.btn_ok), getString(R.string.cancel), new CommonUtils.IL() {
            @Override
            public void onSuccess() {
                try {
                    HashMap<String, String> map = new HashMap<>();
                    map.put(REQUEST_KEY_REQUEST_ID, detailReportList.getID());
                    map.put(REQUEST_KEY_REQUEST_ITEM_ID, item.getId());
                    map.put(REQUEST_KEY_SUM_PRICE, detailReportList.getSumPrice());
                    map.put(REQUEST_KEY_STUFFS_ACCOUNT, detailReportList.getStuffCount());
                    if (LOGTRUE)
                        Log.d("mPARAMS ::::: ", map.toString());
                    mDetailReportListViewModel.deleteItemDetailReportList(DetailReportListActivity.this, iCallApi, map);
                } catch (Exception e) {
                    CommonUtils.showSingleButtonAlert(DetailReportListActivity.this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancel() {

            }
        });

    }

    public void setForm(){

        detailReportList.setStuffCount(String.valueOf(Integer.parseInt(detailReportList.getStuffCount()) - 1));
        int priceKoll = 0;
        for (int i = 0; i < detailRequestResponses.size(); i++) {
            priceKoll = priceKoll + (Integer.parseInt(detailRequestResponses.get(i).getPrice()) * Integer.parseInt(detailRequestResponses.get(i).getCount())) -
                    ((Integer.parseInt(detailRequestResponses.get(i).getPrice()) * Integer.parseInt(detailRequestResponses.get(i).getCount()) / 100) *
                            Integer.parseInt(detailRequestResponses.get(i).getOffer()));
        }
        mActivityDetailReportListBinding.sumFactor.setText(numberFormat.format(priceKoll));
        detailReportList.setSumPrice(String.valueOf(priceKoll));
    }

    private void callEditItem(DetailReportListResponse item, int position) {
        showTwoButtonAlertForEdit(position, item, this, "لطفا تعداد مورد نظر را وارد کنید.", "ویرایش", getString(R.string.cancel));
    }

    public void showTwoButtonAlertForEdit(final int position, final DetailReportListResponse item, Context context, String msg, String buttonOk, String btnCancel) {
        try {
            TextView tvAlertMessage;
            ImageView btnAlertCancel;
            Button btnOk, btCancel;
            View dialogAlert = LayoutInflater.from(context).inflate(R.layout.dialog_edit_alert, null);
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(dialogAlert);
            WindowManager.LayoutParams windowLayout = dialog.getWindow().getAttributes();
            windowLayout.gravity = Gravity.CENTER;
            tvAlertMessage = (TextView) dialogAlert.findViewById(R.id.tv_alert_message);
            btnOk = (Button) dialogAlert.findViewById(R.id.btnOk);
            btnOk.setText(buttonOk);
            btCancel = (Button) dialogAlert.findViewById(R.id.btnCancel);
            final EditText edit = (EditText) dialogAlert.findViewById(R.id.edit);
            btCancel.setText(btnCancel);
            btnAlertCancel = (ImageView) dialogAlert.findViewById(R.id.btn_alert_cancel);

            if (!TextUtils.isEmpty(msg))
                tvAlertMessage.setText(msg);
            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callEdit(edit.getText().toString(), item, position);
                    dialog.dismiss();
                }
            });
            btnAlertCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callEdit(String text, DetailReportListResponse item, int position) {
        try {
            detailReportList.setSumPrice(
                    String.valueOf((Integer.parseInt(detailReportList.getSumPrice()) -
                            (Integer.parseInt(item.getCount()) *
                                    Integer.parseInt(item.getPrice())))));
            detailReportList.setSumPrice(
                    String.valueOf(Integer.parseInt(detailReportList.getSumPrice()) +
                            (Integer.parseInt(item.getPrice()) *
                                    Integer.parseInt(text))));
            mActivityDetailReportListBinding.sumFactor.setText(numberFormat.format(Integer.parseInt(detailReportList.getSumPrice())));
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_REQUEST_ID, detailReportList.getID());
            map.put(REQUEST_KEY_REQUEST_ITEM_ID, item.getId());
            map.put(REQUEST_KEY_SUM_PRICE, detailReportList.getSumPrice());
            map.put(REQUEST_KEY_ITEM_COUNT, text);
            map.put(REQUEST_KEY_CUSTOMER_PRICE, item.getConsumerPrice());
            map.put(REQUEST_KEY_PRICE, item.getPrice());
            map.put(REQUEST_KEY_OFFER, item.getOffer());
            map.put(REQUEST_KEY_ITEM_SUM_PRICE, String.valueOf((Integer.parseInt(item.getCount()) * Integer.parseInt(item.getPrice()))));
            if (LOGTRUE)
                Log.d("mPARAMS ::::: ", map.toString());
            mDetailReportListViewModel.editItemDetailReportList(DetailReportListActivity.this, iCallApi, map);
            if (item.getOffer() != null)
                editItem(Integer.parseInt(text), Integer.parseInt(item.getPrice()), position, Integer.parseInt(item.getOffer()));
            else
                editItem(Integer.parseInt(text), Integer.parseInt(item.getPrice()), position, 0);

        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(DetailReportListActivity.this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }

    private void editItem(int count, int price, int position, int offer) {
        detailRequestResponses.get(position).setCount(String.valueOf(count));
        if (offer > 0)
            detailRequestResponses.get(position).setSumPrice(String.valueOf((count * price) - (((count * price) / 100) * offer)));
        else
            detailRequestResponses.get(position).setSumPrice(String.valueOf((count * price)));

        int priceKoll = 0;
        for (int i = 0; i < detailRequestResponses.size(); i++) {
            priceKoll = priceKoll + (Integer.parseInt(detailRequestResponses.get(i).getPrice()) * Integer.parseInt(detailRequestResponses.get(i).getCount())) -
                    ((Integer.parseInt(detailRequestResponses.get(i).getPrice()) * Integer.parseInt(detailRequestResponses.get(i).getCount()) / 100) *
                            Integer.parseInt(detailRequestResponses.get(i).getOffer()));
        }
        mActivityDetailReportListBinding.sumFactor.setText(numberFormat.format(priceKoll));

        detailReportListAdapter.clearItems();
        detailReportListAdapter.addItems(detailRequestResponses);
        detailReportListAdapter.notifyItemChanged(position);
    }

    @Override
    public void openRequestList() {
        Intent intent = new Intent(ReportListActivity.getStartIntent(this));
        startActivity(intent);
    }

    @Override
    public void callDetailList() {
        getDetailReportList();
    }
}