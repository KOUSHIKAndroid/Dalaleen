package com.dalaleen.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dalaleen.Activities.DetailsActivity;
import com.dalaleen.R;
import com.dalaleen.custome_front.LatoRegular;
import com.dalaleen.Pojo.SetGetDrawing;
import com.dalaleen.helper.DeviceData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by su on 4/7/17.
 */

public class DrawingsRecyclerViewAdapter extends RecyclerView.Adapter<DrawingsRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> drawingArrayList;
    public DrawingsRecyclerViewAdapter(Context context,ArrayList<String> drawingArrayList){
        this.context=context;
        this.drawingArrayList=drawingArrayList;
    }
    @Override
    public DrawingsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_drawings, parent, false);
        return new DrawingsRecyclerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DrawingsRecyclerViewAdapter.MyViewHolder holder, int position) {

        Picasso.with(context)
                .load(drawingArrayList.get(position)).resize(DeviceData.DeviceWidth((DetailsActivity)context)/2,DeviceData.DeviceWidth((DetailsActivity)context)/2)
                .placeholder(R.drawable.ic_user_placeholder_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(holder.img_view);

    }

    @Override
    public int getItemCount() {
        return drawingArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_view;
        public MyViewHolder(View itemView) {
            super(itemView);
            img_view= (ImageView) itemView.findViewById(R.id.img_view);

        }
    }
}
