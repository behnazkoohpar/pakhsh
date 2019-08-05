package com.koohpar.dstrbt.ui.categoryStuff;

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
import com.koohpar.dstrbt.data.model.api.CategoryStuffResponse;
import com.koohpar.dstrbt.data.model.api.database.StuffSelected;
import com.koohpar.dstrbt.ui.main.MainActivity;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by cmos on 14/04/2018.
 */

public class CategoryStuffAdapter extends RecyclerView.Adapter<CategoryStuffAdapter.ViewHolder> {
    public List<CategoryStuffResponse> stList;
    public static Context context;
    private CategoryStuffAdapter.OnItemClickListener mListener;

    NumberFormat numberFormat = NumberFormat.getNumberInstance();
    public CategoryStuffAdapter(List<CategoryStuffResponse> SlistS) {
        stList = SlistS;
    }

    public interface OnItemClickListener {
        void onIncreaseClick(int position);
    }

    public void setOnitemclickListener(CategoryStuffAdapter.OnItemClickListener onitemclickListener) {
        mListener = onitemclickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView, shopping;
        public final TextView price, name, percent,sale,callMe,numbercarton,offer;

        public ViewHolder(final View itemView, final CategoryStuffAdapter.OnItemClickListener listener) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
            price = (TextView) itemView.findViewById(R.id.price);
            offer = (TextView) itemView.findViewById(R.id.offer);
            name = (TextView) itemView.findViewById(R.id.name);
            numbercarton = (TextView) itemView.findViewById(R.id.numbercarton);
            percent = (TextView) itemView.findViewById(R.id.percent);
            shopping = (ImageView) itemView.findViewById(R.id.shopping);
            sale = (TextView) itemView.findViewById(R.id.sale);
            callMe = (TextView) itemView.findViewById(R.id.callMe);

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

    public CategoryStuffAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_stuff, parent, false);
        CategoryStuffAdapter.ViewHolder viewHolder = new CategoryStuffAdapter.ViewHolder(itemLayoutView, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CategoryStuffAdapter.ViewHolder viewHolder, final int position) {
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
            viewHolder.callMe.setVisibility(View.VISIBLE);
            viewHolder.shopping.setVisibility(View.GONE);
        }
        if (StuffSelected.findByGUID(this.stList.get(position).getID()) == null)
            viewHolder.shopping.setVisibility(this.stList.get(position).getVisibility());
        else
            viewHolder.shopping.setVisibility(View.GONE);
            viewHolder.callMe.setVisibility(View.GONE);
    }

    public int getItemCount() {
        if (this.stList != null)
            return this.stList.size();
        return 0;
    }
}
