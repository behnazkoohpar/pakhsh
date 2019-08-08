package com.koohpar.dstrbt.ui.brand;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.model.api.BrandResponse;
import com.koohpar.dstrbt.ui.categoryStuff.CategoryStuffActivity;

import java.util.List;

/**
 * Created by cmos on 14/04/2018.
 */

public class BrandRecycleViewAdapter extends RecyclerView.Adapter<BrandRecycleViewAdapter.ViewHolder> {
    public List<BrandResponse> stList;
    public static Context context;

    public BrandRecycleViewAdapter(List<BrandResponse> SlistS) {
        stList = SlistS;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView name;

        public ViewHolder(final View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);

        }
    }

    public BrandRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand, parent, false);
        BrandRecycleViewAdapter.ViewHolder viewHolder = new BrandRecycleViewAdapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BrandRecycleViewAdapter.ViewHolder viewHolder, final int position) {
        if (stList.get(position).getPicture() != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(stList.get(position).getPicture().getData(), 0, stList.get(position).getPicture().getData().length);
            viewHolder.imageView.setImageBitmap(bmp);
        }
        viewHolder.name.setText(stList.get(position).getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CategoryStuffActivity.getStartIntent(context,null , stList.get(position).getID(),null);
                context.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        if (this.stList != null)
            return this.stList.size();
        return 0;
    }
}
