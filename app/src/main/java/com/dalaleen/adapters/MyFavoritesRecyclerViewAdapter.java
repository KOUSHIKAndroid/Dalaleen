package com.dalaleen.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.dalaleen.R;
import com.dalaleen.custome_front.LatoBoldTV;
import com.dalaleen.custome_front.LatoRegular;
import com.dalaleen.logger.Logger;
import com.dalaleen.Pojo.SetGetMyFavorites;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by su on 4/13/17.
 */

public class MyFavoritesRecyclerViewAdapter extends RecyclerView.Adapter<MyFavoritesRecyclerViewAdapter.MyViewHolder> {

    int selectedDeletedPos=0;
    ArrayList<SetGetMyFavorites> myFavoritesArrayList;
    Context context;

    public MyFavoritesRecyclerViewAdapter(Context context,ArrayList<SetGetMyFavorites> myFavoritesArrayList){
        this.context=context;
        this.myFavoritesArrayList=myFavoritesArrayList;
    }

    @Override
    public MyFavoritesRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_my_favorites, parent, false);
        return new MyFavoritesRecyclerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        holder.linear_layout_main.getLayoutParams().width =displayMetrics.widthPixels;

            Picasso.with(context)
                    .load(myFavoritesArrayList.get(position).getImg())
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

            holder.tv_name.setText(myFavoritesArrayList.get(position).getName());
            holder.tv_place.setText(myFavoritesArrayList.get(position).getPlace());
            holder.tv_iqu.setText(myFavoritesArrayList.get(position).getIqu());

            holder.relative_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //myFavoritesArrayList.remove(position);

                    //notifyItemRemoved(position);
                    //notifyItemRangeChanged(position, myFavoritesArrayList.size());

                    for (int i=0;i<myFavoritesArrayList.size();i++){

                        if(selectedDeletedPos==i){

                        }
                        else {
                            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                            holder.linear_layout_main.getLayoutParams().width =displayMetrics.widthPixels;
                        }
                    }


                }
            });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.horizontalScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    Logger.showInfo("ScrollView","scrollX_"+scrollX+"_scrollY_"+scrollY+"_oldScrollX_"+oldScrollX+"_oldScrollY_"+oldScrollY);
                    //Do something
                    Logger.showInfo("position",""+position);

                    selectedDeletedPos=position;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return myFavoritesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_view;
        LatoRegular tv_name,tv_place;
        LatoBoldTV tv_iqu;
        RelativeLayout relative_delete;
        LinearLayout  linear_layout_main;
        HorizontalScrollView horizontalScrollView;

        public MyViewHolder(View itemView) {
            super(itemView);
            img_view= (ImageView) itemView.findViewById(R.id.img_view);
            tv_name= (LatoRegular) itemView.findViewById(R.id.tv_name);
            tv_place= (LatoRegular) itemView.findViewById(R.id.tv_place);
            tv_iqu= (LatoBoldTV) itemView.findViewById(R.id.tv_iqu);
            relative_delete= (RelativeLayout) itemView.findViewById(R.id.relative_delete);
            linear_layout_main= (LinearLayout) itemView.findViewById(R.id.linear_layout_main);
            horizontalScrollView= (HorizontalScrollView) itemView.findViewById(R.id.scrollview);
        }
    }
}
