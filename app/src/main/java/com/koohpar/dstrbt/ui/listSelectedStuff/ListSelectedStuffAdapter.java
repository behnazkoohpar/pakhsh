package com.koohpar.dstrbt.ui.listSelectedStuff;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.model.api.CategoryStuffResponse;
import com.koohpar.dstrbt.data.model.api.database.StuffSelected;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by cmos on 04/05/2018.
 */

public class ListSelectedStuffAdapter extends RecyclerView.Adapter<ListSelectedStuffAdapter.ViewHolder> {
    public List<StuffSelected> stList;
    public static Context context;
    private ListSelectedStuffAdapter.OnItemClickListener mListener;
NumberFormat numberFormat = NumberFormat.getNumberInstance();
    public ListSelectedStuffAdapter(List<StuffSelected> SlistS) {
        stList = SlistS;
    }

    public interface OnItemClickListener {
        void onIncreaseClick(int position);

        void onDecreaseClick(int position);
    }

    public void setOnitemclickListener(ListSelectedStuffAdapter.OnItemClickListener onitemclickListener) {
        mListener = onitemclickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        public final TextView price, name, number, brand, sumprice,sale,cartonnumber,offer;
        private final ImageView increase, decrease;

        public ViewHolder(final View itemView, final ListSelectedStuffAdapter.OnItemClickListener listener) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
            increase = (ImageView) itemView.findViewById(R.id.increase);
            decrease = (ImageView) itemView.findViewById(R.id.decrease);
            price = (TextView) itemView.findViewById(R.id.price);
            cartonnumber = (TextView) itemView.findViewById(R.id.cartonnumber);
            sale = (TextView) itemView.findViewById(R.id.consumerPrice);
            sumprice = (TextView) itemView.findViewById(R.id.sumprice);
            name = (TextView) itemView.findViewById(R.id.name);
            offer = (TextView) itemView.findViewById(R.id.offer);
            brand = (TextView) itemView.findViewById(R.id.brand);
            number = (TextView) itemView.findViewById(R.id.number);

            increase.setOnClickListener(new View.OnClickListener() {
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
            decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDecreaseClick(position);
                        }
                    }
                }
            });
        }
    }

    public ListSelectedStuffAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_selected, parent, false);
        ListSelectedStuffAdapter.ViewHolder viewHolder = new ListSelectedStuffAdapter.ViewHolder(itemLayoutView, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListSelectedStuffAdapter.ViewHolder viewHolder, final int position) {
        if (stList.get(position).getPicture() != null) {
            byte[] decodedString = Base64.decode(stList.get(position).getPicture(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            imgProfile.setImageBitmap(decodedByte);
            viewHolder.imageView.setImageBitmap(decodedByte);
        }
        viewHolder.price.setText(numberFormat.format(Integer.parseInt(stList.get(position).getPrice())));
        viewHolder.cartonnumber.setText(stList.get(position).getUnitPrice());
//        viewHolder.sumprice.setText(numberFormat.format(Integer.parseInt(stList.get(position).getPrice())*stList.get(position).getNumberSelected()));
        viewHolder.sumprice.setText(stList.get(position).getSumPrice());
        viewHolder.brand.setText(stList.get(position).getBrandName());
        viewHolder.sale.setText(stList.get(position).getConsumerPrice());
        viewHolder.number.setText(String.valueOf(stList.get(position).getNumberSelected()));
        viewHolder.offer.setText(stList.get(position).getOffer());
        viewHolder.name.setText(this.stList.get(position).getStuffName());

    }

    public int getItemCount() {
        if (this.stList != null)
            return this.stList.size();
        return 0;
    }
}
