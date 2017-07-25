package com.dalaleen.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.dalaleen.Activities.DetailsActivity;
import com.dalaleen.Activities.MyPropertiesActivity;
import com.dalaleen.R;
import com.dalaleen.custome_front.LatoBoldTV;
import com.dalaleen.custome_front.LatoRegular;
import com.dalaleen.Pojo.MyProperties;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by su on 4/17/17.
 */

public class MyPropertiesListRecyclerViewAdapter extends RecyclerView.Adapter<MyPropertiesListRecyclerViewAdapter.MyViewHolder>{
    Context context;
    ArrayList<MyProperties> myPropertiesArrayList;
    int StartFrom=0;

    public MyPropertiesListRecyclerViewAdapter(Context context, ArrayList<MyProperties> myPropertiesArrayList, int start_from){
        this.myPropertiesArrayList=myPropertiesArrayList;
        this.context=context;
        this.StartFrom=start_from;
    }

    @Override
    public MyPropertiesListRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_my_properties, parent, false);
        return new MyPropertiesListRecyclerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyPropertiesListRecyclerViewAdapter.MyViewHolder holder, int position) {
        final MyProperties properties=myPropertiesArrayList.get(position);

        Picasso.with(context)
                .load(properties.getProperty_image())
                .placeholder(R.drawable.ic_error_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(holder.img_view_background);

        holder.tv_name.setText(properties.getName());
        holder.tv_details.setText(properties.getAddress());
        holder.tv_iqu.setText(properties.getPrice());



        holder.totalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,DetailsActivity.class);
                intent.putExtra("DATA",properties.getDetails());
                Pair<View, String> p1 = Pair.create((View)holder.img_view_background, "P_Image");
//                    Pair<View, String> p2 = Pair.create((View)navigationView.findViewById(R.id.walet_amunt), "walletAmount");
                ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation((MyPropertiesActivity)context,p1);
                context.startActivity(intent,activityOptionsCompat.toBundle());
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        if(StartFrom>0 && (position+1)==StartFrom)
        {
            ((MyPropertiesActivity)context).getMypropertiesData(StartFrom);

        }
        return super.getItemViewType(position);


    }

    @Override
    public int getItemCount() {
        return myPropertiesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View totalView;
        ImageView img_view_background;
        LatoRegular tv_name,tv_details;
        LatoBoldTV tv_iqu;

        public MyViewHolder(View itemView) {
            super(itemView);

            img_view_background= (ImageView) itemView.findViewById(R.id.img_view_background);

            tv_name= (LatoRegular) itemView.findViewById(R.id.tv_name);
            tv_details= (LatoRegular) itemView.findViewById(R.id.tv_details);

            tv_iqu= (LatoBoldTV) itemView.findViewById(R.id.tv_iqu);

            totalView=itemView;
        }
    }
}
