package com.koohpar.dstrbt.ui.reportList;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koohpar.dstrbt.data.model.api.ReportListResponse;
import com.koohpar.dstrbt.databinding.ItemReportListBinding;
import com.koohpar.dstrbt.ui.base.BaseViewHolder;

import java.util.List;

/**
 * Created by cmos on 25/06/2018.
 */

public class ReportListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<ReportListResponse> items;
    private Context mContext;
    private OnNewsClickListener mListener ;
    public static final int VIEW_TYPE_NORMAL = 1;

    public ReportListAdapter(Context mContext,List<ReportListResponse> items)
    {
        this.mContext = mContext;
        this.items = items;
    }

    public void setListener(ReportListAdapter.OnNewsClickListener mListener){
        this.mListener = mListener ;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemReportListBinding itemReportListBinding = ItemReportListBinding.inflate
                (LayoutInflater.from(parent.getContext())
                        ,parent,false);
        return new ReportListViewHolder(itemReportListBinding);
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

    public void addItems(List<ReportListResponse> items) {
        clearItems();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        items.clear();
        notifyDataSetChanged();
    }

    public class ReportListViewHolder extends BaseViewHolder{

        ItemReportListBinding binding;
        private  ReportListResponse item;
        private ReportListItemViewModel mReportListItemViewModel;

        int position;


        public ReportListViewHolder(ItemReportListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            this.position = position;
            item = items.get(position);
            mReportListItemViewModel = new ReportListItemViewModel(item);
            binding.setViewModel(mReportListItemViewModel);

            binding.cardReportListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClickListener(item);
                }
            });

            binding.executePendingBindings();

        }
    }

    public interface OnNewsClickListener {
        void onItemClickListener(ReportListResponse news);
    }
}

