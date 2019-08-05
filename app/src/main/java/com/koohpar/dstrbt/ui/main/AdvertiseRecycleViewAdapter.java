package com.koohpar.dstrbt.ui.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.model.api.BannerResponse;

import org.json.JSONException;
import org.json.simple.JSONArray;

import java.util.List;

/**
 * Created by cmos on 10/04/2018.
 */

public class AdvertiseRecycleViewAdapter extends RecyclerView.Adapter<AdvertiseRecycleViewAdapter.ViewHolder> {
    public List<BannerResponse> stList;
    public static Context context;
    public AdvertiseRecycleViewAdapter(List<BannerResponse> SlistS) {
        stList = SlistS;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        public ViewHolder(final View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    public AdvertiseRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advertise, parent, false);
        AdvertiseRecycleViewAdapter.ViewHolder viewHolder = new AdvertiseRecycleViewAdapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdvertiseRecycleViewAdapter.ViewHolder viewHolder, final int position) {
        Bitmap bmp = BitmapFactory.decodeByteArray(stList.get(position).getPicture().getData(), 0, stList.get(position).getPicture().getData().length);
        viewHolder.imageView.setImageBitmap(bmp);
    }

    public int getItemCount() {
        if (this.stList != null)
            return this.stList.size();
        return 0;
    }
}
