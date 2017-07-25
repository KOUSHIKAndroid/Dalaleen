package com.dalaleen.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dalaleen.Pojo.Catagory;
import com.dalaleen.R;
import com.dalaleen.custome_front.LatoLightTV;
import com.dalaleen.custome_front.LatoRegular;
import com.dalaleen.fragments.SearchFilterFragment;
import com.dalaleen.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by apple on 26/04/17.
 */

public class CatagoryPopupAdapter extends RecyclerView.Adapter<CatagoryPopupAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Catagory> CatgoryList;
    SearchFilterFragment searchFilterFragment;
    public CatagoryPopupAdapter(SearchFilterFragment searchFilterFragment, Context context, ArrayList<Catagory> List) {
        this.mContext=context;
        this.CatgoryList=List;
        this.searchFilterFragment=searchFilterFragment;
    }

    @Override
    public CatagoryPopupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.catagory_popup_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(CatagoryPopupAdapter.ViewHolder holder, int position) {
        final Catagory catagory=CatgoryList.get(position);
        Logger.showInfo("ICON--",catagory.getIcon());
        Glide.with(mContext).load(catagory.getIcon()).error(R.drawable.ic_error_black_24dp).into(holder.icon);
        holder.Title.setText(catagory.getTitle_eng());

        holder.Title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFilterFragment.setCatagory(catagory);
            }
        });


    }

    @Override
    public int getItemCount() {
        return CatgoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView icon;
        LatoLightTV Title;

        public ViewHolder(View itemView) {
            super(itemView);
            icon=(ImageView)itemView.findViewById(R.id.IMG_icon);
            Title=(LatoLightTV)itemView.findViewById(R.id.TXT_ITEM);
        }
    }
}
