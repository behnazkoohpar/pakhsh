package com.koohpar.dstrbt.ui.notifyInventory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.model.api.CategoryStuffResponse;

import java.text.NumberFormat;
import java.util.List;

public class ListNotifyInventoryAdapter extends RecyclerView.Adapter<ListNotifyInventoryAdapter.ViewHolder> {
    public List<CategoryStuffResponse> stList;
    public static Context context;
    private ListNotifyInventoryAdapter.OnItemClickListener mListener;

    NumberFormat numberFormat = NumberFormat.getNumberInstance();

    public ListNotifyInventoryAdapter(List<CategoryStuffResponse> SlistS) {
        stList = SlistS;
    }

    public interface OnItemClickListener {
        void onIncreaseClick(int position);
    }

    public void setOnitemclickListener(ListNotifyInventoryAdapter.OnItemClickListener onitemclickListener) {
        mListener = onitemclickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView, shopping;
        public final TextView price, name, percent, sale, numbercarton, offer;

        public ViewHolder(final View itemView, final ListNotifyInventoryAdapter.OnItemClickListener listener) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
            price = (TextView) itemView.findViewById(R.id.price);
            offer = (TextView) itemView.findViewById(R.id.offer);
            name = (TextView) itemView.findViewById(R.id.name);
            numbercarton = (TextView) itemView.findViewById(R.id.numbercarton);
            percent = (TextView) itemView.findViewById(R.id.percent);
            shopping = (ImageView) itemView.findViewById(R.id.shopping);
            sale = (TextView) itemView.findViewById(R.id.sale);

            shopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            shopping.setVisibility(View.GONE);
                            listener.onIncreaseClick(position);

                        }
                    }
                }
            });
        }
    }

    public ListNotifyInventoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_stuff, parent, false);
        ListNotifyInventoryAdapter.ViewHolder viewHolder = new ListNotifyInventoryAdapter.ViewHolder(itemLayoutView, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListNotifyInventoryAdapter.ViewHolder viewHolder, final int position) {
        if (stList.get(position).getPicture() != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(stList.get(position).getPicture().getData(), 0, stList.get(position).getPicture().getData().length);
            viewHolder.imageView.setImageBitmap(bmp);
        }
        viewHolder.numbercarton.setText(stList.get(position).getUnitName());
        viewHolder.offer.setText(stList.get(position).getOffer());
        viewHolder.price.setText(numberFormat.format(Integer.parseInt(stList.get(position).getPrice())));
        viewHolder.sale.setText(numberFormat.format(Integer.parseInt(stList.get(position).getConsumerPrice())));
        viewHolder.name.setText(this.stList.get(position).getStuffName() + " " + this.stList.get(position).getBrandName());
        if (Integer.parseInt(this.stList.get(position).getLastStock()) < Integer.parseInt(this.stList.get(position).getMinimumStuck())) {
            viewHolder.shopping.setVisibility(View.GONE);
        }
    }

    public int getItemCount() {
        if (this.stList != null)
            return this.stList.size();
        return 0;
    }
}
