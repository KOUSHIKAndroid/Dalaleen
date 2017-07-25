package com.dalaleen.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dalaleen.R;
import com.dalaleen.custome_front.LatoBoldTV;
import com.dalaleen.custome_front.LatoRegular;
import com.dalaleen.Pojo.SetGetLocationFragmentOfDetails;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by su on 4/7/17.
 */

public class LocationFragmentOfDetailsRecyclerViewAdapter extends RecyclerView.Adapter<LocationFragmentOfDetailsRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<SetGetLocationFragmentOfDetails> relatedArrayList;


    public LocationFragmentOfDetailsRecyclerViewAdapter(Context context, ArrayList<SetGetLocationFragmentOfDetails> relatedArrayList){
        this.context=context;
        this.relatedArrayList=relatedArrayList;
    }
    @Override
    public LocationFragmentOfDetailsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_location_fragment_of_details, parent, false);
        return new LocationFragmentOfDetailsRecyclerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LocationFragmentOfDetailsRecyclerViewAdapter.MyViewHolder holder, int position) {

        Transformation transformation = new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                int targetWidth = holder.img_view.getWidth();

                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }

            @Override
            public String key() {
                return "transformation" + " desiredWidth";
            }
        };


        Picasso.with(context)
                .load(relatedArrayList.get(position).getImg())
                .placeholder(R.drawable.ic_user_placeholder_black_24dp)
                .error(R.drawable.background)
                .transform(transformation)
                .into(holder.img_view, new Callback() {
                    @Override
                    public void onSuccess() {
//                        holder.progressBar_picture.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });

        holder.tv_name.setText(relatedArrayList.get(position).getName());
        holder.tv_place.setText(relatedArrayList.get(position).getPlace());
        holder.tv_bhk_value.setText(relatedArrayList.get(position).getBhk());
        holder.tv_bhk_sqr_ft.setText(relatedArrayList.get(position).getSqrft());
    }

    @Override
    public int getItemCount() {
        return relatedArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_view;
        LatoBoldTV tv_name,tv_bhk_value,tv_bhk_sqr_ft;
        LatoRegular tv_place;
        public MyViewHolder(View itemView) {
            super(itemView);
            img_view= (ImageView) itemView.findViewById(R.id.img_view);
            tv_name= (LatoBoldTV) itemView.findViewById(R.id.tv_name);
            tv_place= (LatoRegular) itemView.findViewById(R.id.tv_place);
            tv_bhk_value= (LatoBoldTV) itemView.findViewById(R.id.tv_bhk_value);
            tv_bhk_sqr_ft= (LatoBoldTV) itemView.findViewById(R.id.tv_bhk_sqr_ft);
        }
    }
}
