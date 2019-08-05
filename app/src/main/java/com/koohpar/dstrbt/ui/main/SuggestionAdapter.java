package com.koohpar.dstrbt.ui.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.model.api.SpecialOfferResponse;
import com.koohpar.dstrbt.data.model.api.database.StuffSelected;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by cmos on 11/04/2018.
 */

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.ViewHolder> {
    public List<SpecialOfferResponse> stList;
    public static Context context;
    private OnItemClickListener mListener;
    NumberFormat numberFormat = NumberFormat.getNumberInstance();

    public SuggestionAdapter(List<SpecialOfferResponse> SlistS) {
        stList = SlistS;
    }

    public interface OnItemClickListener {
        void onIncreaseClick(int position);
    }

    public void setOnitemclickListener(OnItemClickListener onitemclickListener) {
        mListener = onitemclickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView, shopping;
        public final TextView price, name, consumerPrice, percent, callMe,numbercarton;

        public ViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
            shopping = (ImageView) itemView.findViewById(R.id.shopping);
            price = (TextView) itemView.findViewById(R.id.price);
            numbercarton = (TextView) itemView.findViewById(R.id.numbercarton);
            name = (TextView) itemView.findViewById(R.id.name);
            percent = (TextView) itemView.findViewById(R.id.percent);
            consumerPrice = (TextView) itemView.findViewById(R.id.consumerPrice);
            callMe = (TextView) itemView.findViewById(R.id.callMe);

            shopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onIncreaseClick(position);
                        }
                    }
                }
            });
        }
    }

    public SuggestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suggestion, parent, false);
        SuggestionAdapter.ViewHolder viewHolder = new SuggestionAdapter.ViewHolder(itemLayoutView, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SuggestionAdapter.ViewHolder viewHolder, final int position) {
        Bitmap bmp = BitmapFactory.decodeByteArray(stList.get(position).getPicture().getData(), 0, stList.get(position).getPicture().getData().length);
        viewHolder.imageView.setImageBitmap(bmp);
        viewHolder.price.setText(numberFormat.format(Integer.parseInt(stList.get(position).getPrice())));
        viewHolder.numbercarton.setText(stList.get(position).getUnitName());
        viewHolder.consumerPrice.setText(numberFormat.format(Integer.parseInt(stList.get(position).getConsumerPrice())));
        viewHolder.name.setText(this.stList.get(position).getStuffName() + " " + this.stList.get(position).getBrandName());
        viewHolder.percent.setText(String.valueOf(this.stList.get(position).getOffer()));
        if (Integer.parseInt(this.stList.get(position).getLastStock()) < Integer.parseInt(this.stList.get(position).getMinimumStuck())) {
            viewHolder.callMe.setVisibility(View.VISIBLE);
            viewHolder.shopping.setVisibility(View.GONE);
        }

        if (StuffSelected.findByGUID(this.stList.get(position).getID()) == null)
            viewHolder.shopping.setVisibility(this.stList.get(position).getVisibility());
        else {
            viewHolder.shopping.setVisibility(View.GONE);
            viewHolder.callMe.setVisibility(View.GONE);
        }
    }

    public int getItemCount() {
        if (this.stList != null)
            return this.stList.size();
        return 0;
    }
}
