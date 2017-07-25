package com.dalaleen.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.dalaleen.R;
import com.dalaleen.custome_front.LatoBoldTV;
import com.dalaleen.custome_front.LatoRegular;
import com.dalaleen.Pojo.SetGetNearBy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by su on 4/11/17.
 */

public class NearByRecyclerViewAdapter extends RecyclerView.Adapter<NearByRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<SetGetNearBy> nearByArrayList;

    public  NearByRecyclerViewAdapter(Context context,ArrayList<SetGetNearBy> nearByArrayList){
        this.context=context;
        this.nearByArrayList=nearByArrayList;
    }

    @Override
    public NearByRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_near_by,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NearByRecyclerViewAdapter.MyViewHolder holder, int position) {

        Picasso.with(context)
                .load(nearByArrayList.get(position).getImg())
                .placeholder(R.drawable.ic_user_placeholder_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(holder.img_view);

        holder.tv_name.setText(nearByArrayList.get(position).getName());
        holder.tv_bhk.setText(nearByArrayList.get(position).getBhk());
        holder.tv_address.setText(nearByArrayList.get(position).getAddress());

    }

    @Override
    public int getItemCount() {
        return nearByArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_view;
        LatoBoldTV tv_name,tv_bhk;
        LatoRegular tv_address;
        public MyViewHolder(View itemView) {
            super(itemView);

            img_view= (ImageView) itemView.findViewById(R.id.img_view);
            tv_name= (LatoBoldTV) itemView.findViewById(R.id.tv_name);
            tv_bhk= (LatoBoldTV) itemView.findViewById(R.id.tv_bhk);
            tv_address= (LatoRegular) itemView.findViewById(R.id.tv_address);
        }
    }
}
