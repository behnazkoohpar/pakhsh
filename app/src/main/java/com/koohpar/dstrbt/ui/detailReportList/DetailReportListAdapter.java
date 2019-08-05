package com.koohpar.dstrbt.ui.detailReportList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koohpar.dstrbt.data.model.api.DetailReportListResponse;
import com.koohpar.dstrbt.databinding.ItemDetailReportListBinding;
import com.koohpar.dstrbt.ui.base.BaseViewHolder;

import java.util.List;

/**
 * Created by cmos on 25/06/2018.
 */

public class DetailReportListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<DetailReportListResponse> items;
    private Context mContext;
    public static final int VIEW_TYPE_NORMAL = 1;
    private OnDetailResponseClickListener mListener;

    public void setListener(OnDetailResponseClickListener mListener) {
        this.mListener = mListener;
    }

    public DetailReportListAdapter(Context mContext, List<DetailReportListResponse> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemDetailReportListBinding itemReportListBinding = ItemDetailReportListBinding.inflate
                (LayoutInflater.from(parent.getContext())
                        , parent, false);
        return new DetailReportListViewHolder(itemReportListBinding);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItems(List<DetailReportListResponse> items) {
        clearItems();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        items.clear();
        notifyDataSetChanged();
    }

    public class DetailReportListViewHolder extends BaseViewHolder {

        ItemDetailReportListBinding binding;
        private DetailReportListResponse item;
        private DetailReportListItemViewModel mDetailReportListItemViewModel;

        int position;


        public DetailReportListViewHolder(ItemDetailReportListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(final int position) {
            this.position = position;
            item = items.get(position);
            mDetailReportListItemViewModel = new DetailReportListItemViewModel(item);
            binding.setViewModel(mDetailReportListItemViewModel);

            binding.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onDeleteListener(item);
                }
            });
            binding.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onEditListener(item,position);
                }
            });

            binding.executePendingBindings();
        }

    }
    public interface OnDetailResponseClickListener {
        void onDeleteListener(DetailReportListResponse item);

        void onEditListener(DetailReportListResponse item,int position);
    }
}

