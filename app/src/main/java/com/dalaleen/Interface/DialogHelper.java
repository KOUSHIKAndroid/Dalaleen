package com.dalaleen.Interface;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.dalaleen.R;
import com.dalaleen.adapters.SimpleListAdapter;

import org.json.JSONArray;

/**
 * Created by apple on 14/08/17.
 */

public class DialogHelper {
    Context mContext;
    Dialog AppDialog;
    public interface ListItemClick{
       void OnItemSelct(String data);
        void OnCancel();
    }

    ListItemClick listItemClick;



    public DialogHelper(Context mContext) {
        this.mContext = mContext;
        AppDialog=new Dialog(mContext);
        AppDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


    }

    public  void SelectSimpleList(JSONArray ListItems, ListItemClick listItemClick)
    {
        LayoutInflater        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View popview=inflater.inflate(R.layout.catagorylistpopup,null);
        RecyclerView recyclerView=(RecyclerView)popview.findViewById(R.id.LIST_catgory);
        ImageView Close=(ImageView)popview.findViewById(R.id.IMG_close);

        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDialog.dismiss();
            }
        });
        AppDialog.setContentView(popview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new SimpleListAdapter(mContext, "", this,ListItems));
    }


    public  void SelectSimpleList(JSONArray ListItems, String ParamsName, ListItemClick listItemClick)
    {
        this.listItemClick=listItemClick;
        LayoutInflater        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View popview=inflater.inflate(R.layout.catagorylistpopup,null);
        RecyclerView recyclerView=(RecyclerView)popview.findViewById(R.id.LIST_catgory);
        ImageView Close=(ImageView)popview.findViewById(R.id.IMG_close);

        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDialog.dismiss();
            }
        });
        AppDialog.setContentView(popview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new SimpleListAdapter(mContext,ParamsName,this,ListItems));
        AppDialog.setCancelable(false);
        AppDialog.show();
    }
    public void CalcelDialog(String s)
    {
        AppDialog.dismiss();
        listItemClick.OnItemSelct(s);

    }


}
