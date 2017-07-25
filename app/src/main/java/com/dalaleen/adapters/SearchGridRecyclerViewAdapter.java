package com.dalaleen.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.dalaleen.Activities.DetailsActivity;
import com.dalaleen.R;
import com.dalaleen.custome_front.LatoBoldTV;
import com.dalaleen.custome_front.LatoRegular;
import com.dalaleen.Pojo.SetGetSearchList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by su on 4/17/17.
 */

public class SearchGridRecyclerViewAdapter extends RecyclerView.Adapter<SearchGridRecyclerViewAdapter.MyViewHolder>{
    Context context;
    ArrayList<SetGetSearchList> searchLists;

    public SearchGridRecyclerViewAdapter(Context context, ArrayList<SetGetSearchList> searchLists){
        this.searchLists=searchLists;
        this.context=context;
    }

    @Override
    public SearchGridRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_grid_search_list, parent, false);
        return new SearchGridRecyclerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchGridRecyclerViewAdapter.MyViewHolder holder, int position) {

        Picasso.with(context)
                .load(searchLists.get(position).getImg())
                .placeholder(R.drawable.ic_error_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(holder.img_view_background);

        holder.tv_name.setText(searchLists.get(position).getName());
        holder.tv_details.setText(searchLists.get(position).getRoom_detail());
        holder.tv_iqu.setText(searchLists.get(position).getIQU());
        holder.tv_price.setText(searchLists.get(position).getPrice());


        holder.totalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, DetailsActivity.class);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return searchLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View totalView;
        ImageView img_view_background;
        LatoBoldTV tv_name,tv_iqu;
        LatoRegular tv_details,tv_price;
        public MyViewHolder(View itemView) {
            super(itemView);

            img_view_background= (ImageView) itemView.findViewById(R.id.img_view_background);

            tv_name= (LatoBoldTV) itemView.findViewById(R.id.tv_name);
            tv_iqu= (LatoBoldTV) itemView.findViewById(R.id.tv_iqu);

            tv_details= (LatoRegular) itemView.findViewById(R.id.tv_details);
            tv_price= (LatoRegular) itemView.findViewById(R.id.tv_price);


            totalView=itemView;
        }
    }
}
