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
import com.dalaleen.Pojo.SetGetPostProperty;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by su on 4/12/17.
 */

public class PostPropertyRecyclerViewAdapter extends RecyclerView.Adapter<PostPropertyRecyclerViewAdapter.MyViewHolder> {

    ArrayList<SetGetPostProperty> postPropertyArrayList;
    Context context;

    public PostPropertyRecyclerViewAdapter(Context context,ArrayList<SetGetPostProperty> postPropertyArrayList){
        this.context=context;
        this.postPropertyArrayList=postPropertyArrayList;
    }

    @Override
    public PostPropertyRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_post_property, parent, false);
        return new PostPropertyRecyclerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PostPropertyRecyclerViewAdapter.MyViewHolder holder, int position) {

        Picasso.with(context)
                .load(postPropertyArrayList.get(position).getImg())
                .placeholder(R.drawable.ic_user_placeholder_black_24dp)
                .error(R.drawable.background)
                .into(holder.img_view, new Callback() {
                    @Override
                    public void onSuccess() {
//                        holder.progressBar_picture.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });

        holder.tv_name.setText(postPropertyArrayList.get(position).getName());
        holder.tv_place.setText(postPropertyArrayList.get(position).getPlace());
        holder.tv_iqu.setText(postPropertyArrayList.get(position).getIqu());

    }

    @Override
    public int getItemCount() {
        return postPropertyArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_view;
        LatoRegular tv_name,tv_place;
        LatoBoldTV tv_iqu;
        public MyViewHolder(View itemView) {
            super(itemView);

            img_view= (ImageView) itemView.findViewById(R.id.img_view);
            tv_name= (LatoRegular) itemView.findViewById(R.id.tv_place);
            tv_place= (LatoRegular) itemView.findViewById(R.id.tv_place);
            tv_iqu= (LatoBoldTV) itemView.findViewById(R.id.tv_iqu);
        }
    }
}
