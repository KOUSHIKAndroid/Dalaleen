package com.dalaleen.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dalaleen.Activities.BaseActivity;
import com.dalaleen.Activities.DetailsActivity;
import com.dalaleen.CustomView.LoadingImageView;
import com.dalaleen.CustomView.LoadingTextView;
import com.dalaleen.Pojo.MyProperties;
import com.dalaleen.R;
import com.dalaleen.custome_front.LatoRegular;
import com.dalaleen.fragments.ExplorerFragment;
import com.dalaleen.helper.UserDATAGetting;
import com.dalaleen.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by su on 3/30/17.
 */

public class ExplorerFeaturePropertiesViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<MyProperties> setGetExplorerArrayList;

    private static final int TYPE_FOOTER=2;
    private static final int TYPE_HEADER=0;
    private static final int TYPE_BOADY=1;

    Context context;
    int width = 0;
    int StartP=0;
    boolean LazyLoad=true;
    ExplorerFragment explorerFragment;

    public ExplorerFeaturePropertiesViewAdapter(ExplorerFragment explorerFragment,Context context, ArrayList<MyProperties> setGetExplorerArrayList, int Startp){
        this.context=context;
        this.setGetExplorerArrayList = setGetExplorerArrayList;
        this.StartP=Startp;
        this.explorerFragment=explorerFragment;

        Display display = ((BaseActivity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_BOADY) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.explorer_list_row, parent, false);
            return new MyViewHolder(itemView);
        }else if(viewType==TYPE_FOOTER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.explorer_list_row_loader, parent, false);
            return new LoaderViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holde1, int position) {
        if(holde1 instanceof MyViewHolder) {
            final MyViewHolder holder=(MyViewHolder)holde1;
            final MyProperties properties = setGetExplorerArrayList.get(position);
            if (UserDATAGetting.isTablet(context)) {

                LinearLayout.LayoutParams imglay = new LinearLayout.LayoutParams(UserDATAGetting.dpToPx(300, context), UserDATAGetting.dpToPx(300, context));
                holder.img.setLayoutParams(imglay);
                Logger.showInfo("tablet", "" + UserDATAGetting.isTablet(context));
            } else {

                LinearLayout.LayoutParams imglay = new LinearLayout.LayoutParams(UserDATAGetting.dpToPx(150, context), UserDATAGetting.dpToPx(150, context));
                holder.img.setLayoutParams(imglay);
                Logger.showInfo("tablet", "" + UserDATAGetting.isTablet(context));
            }


            Picasso.with(context)
                    .load(properties.getProperty_image())
                    .placeholder(R.drawable.ic_user_placeholder_black_24dp)
                    .error(R.drawable.ic_error_black_24dp)
                    .into(holder.img);
            holder.tv_name.setText(setGetExplorerArrayList.get(position).getName());
            holder.tv_place.setText(properties.getAddress());
            holder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,DetailsActivity.class);
                    intent.putExtra("DATA",properties.getDetails());
                    Pair<View, String> p1 = Pair.create((View)holder.img, "P_Image");
//                    Pair<View, String> p2 = Pair.create((View)navigationView.findViewById(R.id.walet_amunt), "walletAmount");
                    ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation((BaseActivity)context,p1);
                    context.startActivity(intent,activityOptionsCompat.toBundle());
                }
            });



        }

        else  if(holde1 instanceof LoaderViewHolder){
            Logger.showInfo("LOADER","TTT");
            LoaderViewHolder holder=(LoaderViewHolder)holde1;

            if (UserDATAGetting.isTablet(context)) {

                LinearLayout.LayoutParams imglay = new LinearLayout.LayoutParams(UserDATAGetting.dpToPx(300, context), UserDATAGetting.dpToPx(300, context));
                holder.img.setLayoutParams(imglay);
                Logger.showInfo("tablet", "" + UserDATAGetting.isTablet(context));
            } else {

                LinearLayout.LayoutParams imglay = new LinearLayout.LayoutParams(UserDATAGetting.dpToPx(150, context), UserDATAGetting.dpToPx(150, context));
                holder.img.setLayoutParams(imglay);
                Logger.showInfo("tablet", "" + UserDATAGetting.isTablet(context));
            }
            holder.img.startLoading();
            holder.tv_name.startLoading();
            holder.tv_place.startLoading();

        }
    }

    @Override
    public int getItemViewType(int position) {

       return setGetExplorerArrayList.get(position) != null ? TYPE_BOADY : TYPE_FOOTER;
    }

    @Override
    public int getItemCount() {

            return setGetExplorerArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        LatoRegular tv_name,tv_place;
        CardView card_view;
        public MyViewHolder(View itemView) {
            super(itemView);
            img= (ImageView) itemView.findViewById(R.id.img);
            tv_name= (LatoRegular) itemView.findViewById(R.id.tv_name);
            tv_place= (LatoRegular) itemView.findViewById(R.id.tv_place);
            card_view=(CardView)itemView.findViewById(R.id.card_view);

        }
    }

    public class LoaderViewHolder extends RecyclerView.ViewHolder {
        LoadingImageView img;
        LoadingTextView tv_name,tv_place;
        public LoaderViewHolder(View itemView) {
            super(itemView);
            img= (LoadingImageView) itemView.findViewById(R.id.img);
            tv_name= (LoadingTextView) itemView.findViewById(R.id.tv_name);
            tv_place= (LoadingTextView) itemView.findViewById(R.id.tv_place);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    explorerFragment.LoadFEATUREProperties(StartP);

                }
            },2000);
        }
    }
}
