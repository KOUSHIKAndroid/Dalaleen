package com.dalaleen.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dalaleen.Interface.DialogHelper;
import com.dalaleen.R;

import org.json.JSONArray;

/**
 * Created by apple on 14/08/17.
 */

public class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.ViewHolder> {
    DialogHelper.ListItemClick listItemClick;
    Context mContext;
    JSONArray array;
    public SimpleListAdapter(Context mContext, DialogHelper.ListItemClick listItemClick, JSONArray listItems) {
        this.array=listItems;
        this.listItemClick=listItemClick;
        this.mContext=mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View items=View.inflate(mContext, R.layout.simple_list_items,parent);
        return new ViewHolder(items);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
