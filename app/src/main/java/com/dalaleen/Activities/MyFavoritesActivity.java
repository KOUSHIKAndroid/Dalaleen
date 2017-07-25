package com.dalaleen.Activities;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dalaleen.R;
import com.dalaleen.adapters.MyFavoritesRecyclerViewAdapter;
import com.dalaleen.helper.VerticalSpaceItemDecoration;
import com.dalaleen.Pojo.SetGetMyFavorites;
import java.util.ArrayList;

/**
 * Created by su on 4/13/17.
 */

public class MyFavoritesActivity extends AppCompatActivity {
    Paint p = new Paint();
    private static final int VERTICAL_ITEM_SPACE = 2;
    MyFavoritesRecyclerViewAdapter myFavoritesRecyclerViewAdapter;
    ArrayList<SetGetMyFavorites> myFavoritesArrayList;
    RecyclerView my_favorites_recycler_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorites_activity);


        findViewById(R.id.back2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(null);
//
//        LatoBoldTV mTitle = (LatoBoldTV) toolbar.findViewById(R.id.toolbar_title);
//        mTitle.setText("Post Property");


        my_favorites_recycler_view= (RecyclerView) findViewById(R.id.my_favorites_recycler_view);
        my_favorites_recycler_view.setLayoutManager(new LinearLayoutManager(MyFavoritesActivity.this));
        my_favorites_recycler_view.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));

        myFavoritesArrayList=new ArrayList<>();

        for (int i=0;i<10;i++ ){

            SetGetMyFavorites setGetPostProperty=new SetGetMyFavorites();

            setGetPostProperty.setImg("drawable://" + R.drawable.image);
            setGetPostProperty.setName("Royal Avenue "+i);
            setGetPostProperty.setPlace("Rajarhat,New Town "+i);
            setGetPostProperty.setIqu("IQU 25,000" +i);

            myFavoritesArrayList.add(setGetPostProperty);
        }

        myFavoritesRecyclerViewAdapter=new MyFavoritesRecyclerViewAdapter(MyFavoritesActivity.this,myFavoritesArrayList);
        my_favorites_recycler_view.setAdapter(myFavoritesRecyclerViewAdapter);
    }
}
