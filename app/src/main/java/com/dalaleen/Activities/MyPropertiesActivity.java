package com.dalaleen.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.dalaleen.Interface.CustomAsynctask;
import com.dalaleen.Pojo.MyProperties;
import com.dalaleen.R;
import com.dalaleen.adapters.MyPropertiesGridRecyclerViewAdapter;
import com.dalaleen.adapters.MyPropertiesListRecyclerViewAdapter;
import com.dalaleen.helper.ConstantClass;
import com.dalaleen.helper.VerticalSpaceItemDecoration;
import com.dalaleen.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by su on 4/12/17.
 */

public class MyPropertiesActivity extends AppCompatActivity {

    ImageView Img_list_grid;

    public boolean isListViseView = true;

    private static final int VERTICAL_ITEM_SPACE = 2;

    RecyclerView grid_my_properties_recycler_view;

    ArrayList<MyProperties> myPropertiesArrayList;
    CustomAsynctask customAsynctask;
    MyPropertiesGridRecyclerViewAdapter myPropertiesGridRecyclerViewAdapter;
    MyPropertiesListRecyclerViewAdapter myPropertiesListRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_properties_activity);

        findViewById(R.id.back2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        customAsynctask = new CustomAsynctask(this, new ProgressDialog(this));

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(null);
//
//        LatoBoldTV mTitle = (LatoBoldTV) toolbar.findViewById(R.id.toolbar_title);
//        mTitle.setText("My Properties");

        Img_list_grid = (ImageView) findViewById(R.id.image_view_list_grid);




        grid_my_properties_recycler_view = (RecyclerView) findViewById(R.id.grid_my_properties_recycler_view);
        grid_my_properties_recycler_view.setLayoutManager(new LinearLayoutManager(MyPropertiesActivity.this));
//        grid_my_properties_recycler_view.setItemAnimator(new DefaultItemAnimator());
//        grid_my_properties_recycler_view.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));

        myPropertiesArrayList = new ArrayList<>();
        getMypropertiesData(0);




        Img_list_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isListViseView) {
                    isListViseView = false;
                    Img_list_grid.setImageResource(R.drawable.ic_format_list_bulleted_white_24dp);



//                    if(myPropertiesGridRecyclerViewAdapter==null) {
                    LinearLayoutManager layoutManager = ((LinearLayoutManager)grid_my_properties_recycler_view.getLayoutManager());

                    int currebtposition=layoutManager.findFirstVisibleItemPosition();
                    Logger.showInfo("LINE-","LL "+currebtposition);
                        myPropertiesGridRecyclerViewAdapter = new MyPropertiesGridRecyclerViewAdapter(MyPropertiesActivity.this, myPropertiesArrayList, myPropertiesArrayList.size());
                        grid_my_properties_recycler_view.setAdapter(myPropertiesGridRecyclerViewAdapter);
                    grid_my_properties_recycler_view.scrollToPosition(currebtposition);



//                    }else {
//
//
//                        myPropertiesListRecyclerViewAdapter.onDetachedFromRecyclerView(grid_my_properties_recycler_view);
//                        grid_my_properties_recycler_view.invalidate();
//
////                        grid_my_properties_recycler_view.removeAllViews();
//                        myPropertiesGridRecyclerViewAdapter.onAttachedToRecyclerView(grid_my_properties_recycler_view);
//                        grid_my_properties_recycler_view.invalidate();
//
//                        myPropertiesGridRecyclerViewAdapter.notifyDataSetChanged();
////                        grid_my_properties_recycler_view.swapAdapter(myPropertiesGridRecyclerViewAdapter,false);
//                    }

                } else {
                    isListViseView = true;
                    Img_list_grid.setImageResource(R.drawable.ic_thumbnail_white_24dp);
                    LinearLayoutManager layoutManager = ((LinearLayoutManager)grid_my_properties_recycler_view.getLayoutManager());

                    int currebtposition=layoutManager.findFirstVisibleItemPosition();
                    Logger.showInfo("LINE-"," GG "+layoutManager.getInitialPrefetchItemCount());

//                    if(myPropertiesListRecyclerViewAdapter==null) {
                        myPropertiesListRecyclerViewAdapter = new MyPropertiesListRecyclerViewAdapter(MyPropertiesActivity.this, myPropertiesArrayList, myPropertiesArrayList.size());
                        grid_my_properties_recycler_view.setAdapter(myPropertiesListRecyclerViewAdapter);
                        grid_my_properties_recycler_view.scrollToPosition(currebtposition);
//                    }else {
//                        myPropertiesGridRecyclerViewAdapter.onDetachedFromRecyclerView(grid_my_properties_recycler_view);
//                        grid_my_properties_recycler_view.invalidate();
//
////                        grid_my_properties_recycler_view.removeAllViews();
//                        myPropertiesListRecyclerViewAdapter.onAttachedToRecyclerView(grid_my_properties_recycler_view);
//                        grid_my_properties_recycler_view.invalidate();
//
//                        myPropertiesListRecyclerViewAdapter.notifyDataSetChanged();
////                        grid_my_properties_recycler_view.swapAdapter(myPropertiesListRecyclerViewAdapter,false);
//                    }
                }

            }
        });

    }

    public void getMypropertiesData(int startp) {

        String URL = ConstantClass.BASE_URL + "/Jsonapp_control/user_my_property_list?user_id=" + 1 + "&lang=ar&start_from=" + startp + "&per_page=10";
        customAsynctask.getResultListenerFromAsynctaskForGet(URL, new CustomAsynctask.onAPIResponse() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("response").equalsIgnoreCase("TRUE")){
                        JSONArray array=object.getJSONArray("info");
                        for(int i=0;i<array.length();i++){
                            MyProperties myProperties=new MyProperties();
                            JSONObject jsonObject=array.getJSONObject(i);
                            myProperties.setId(jsonObject.getString("id"));
                            myProperties.setName(jsonObject.getString("name"));
                            myProperties.setAddress(jsonObject.getString("address"));
                            myProperties.setCategory_name(jsonObject.getString("Category_name"));
                            myProperties.setProperty_description(jsonObject.getString("property_description"));
                            myProperties.setPrice(jsonObject.getString("Price"));
                            myProperties.setProperty_image(jsonObject.getString("property_image"));
                            myProperties.setCategory_sub(jsonObject.getString("category_sub"));
                            myProperties.setDetails(jsonObject.getJSONObject("details")+"");
                            myPropertiesArrayList.add(myProperties);

                        }
                        if(myPropertiesListRecyclerViewAdapter==null && isListViseView) {
                            myPropertiesListRecyclerViewAdapter = new MyPropertiesListRecyclerViewAdapter(MyPropertiesActivity.this, myPropertiesArrayList, object.getInt("start_from"));
                            grid_my_properties_recycler_view.setAdapter(myPropertiesListRecyclerViewAdapter);
                        }else if(myPropertiesGridRecyclerViewAdapter==null && isListViseView==false) {
                            myPropertiesGridRecyclerViewAdapter = new MyPropertiesGridRecyclerViewAdapter(MyPropertiesActivity.this, myPropertiesArrayList,object.getInt("start_from"));
                            grid_my_properties_recycler_view.setAdapter(myPropertiesGridRecyclerViewAdapter);
                        }else {
                            myPropertiesListRecyclerViewAdapter.notifyDataSetChanged();
                        }


                    }else {

                    }

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String Error) {

            }
        });

    }
}
