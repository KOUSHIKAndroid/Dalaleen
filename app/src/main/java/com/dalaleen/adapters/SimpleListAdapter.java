package com.dalaleen.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dalaleen.Interface.DialogHelper;
import com.dalaleen.R;
import com.dalaleen.custome_front.LatoRegular;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 14/08/17.
 */

public class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.ViewHolder> {
    DialogHelper listItemClick;
    Context mContext;
    JSONArray array;
    String ParamsName;
    public SimpleListAdapter(Context mContext, String paramsName, DialogHelper listItemClick, JSONArray listItems) {
        this.array=listItems;
        this.listItemClick=listItemClick;
        this.mContext=mContext;
        this.ParamsName=paramsName;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_items, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            final JSONObject items=array.getJSONObject(position);
            holder.Item.setText(items.getString(ParamsName));

            holder.Item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listItemClick.CalcelDialog(items+"");

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LatoRegular Item;
        public ViewHolder(View itemView) {
            super(itemView);
            Item=(LatoRegular)itemView.findViewById(R.id.Item);
        }
    }
}
