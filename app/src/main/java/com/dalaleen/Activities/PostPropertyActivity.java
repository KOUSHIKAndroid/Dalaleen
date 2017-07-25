package com.dalaleen.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dalaleen.R;
import com.dalaleen.adapters.PostPropertyRecyclerViewAdapter;
import com.dalaleen.helper.VerticalSpaceItemDecoration;
import com.dalaleen.Pojo.SetGetPostProperty;
import java.util.ArrayList;

/**
 * Created by su on 4/12/17.
 */

public class PostPropertyActivity extends AppCompatActivity {

    private static final int VERTICAL_ITEM_SPACE = 2;
    ArrayList<SetGetPostProperty> postPropertyArrayList;
    RecyclerView post_recycler_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_property);


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


        post_recycler_view= (RecyclerView) findViewById(R.id.post_recycler_view);
        post_recycler_view.setLayoutManager(new LinearLayoutManager(PostPropertyActivity.this));
        post_recycler_view.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));

        postPropertyArrayList=new ArrayList<>();

        for (int i=0;i<10;i++ ){

            SetGetPostProperty setGetPostProperty=new SetGetPostProperty();

            setGetPostProperty.setImg("drawable://" + R.drawable.image);
            setGetPostProperty.setName("Royal Avenue "+i);
            setGetPostProperty.setPlace("Rajarhat,New Town "+i);
            setGetPostProperty.setIqu("IQU 25,000" +i);

            postPropertyArrayList.add(setGetPostProperty);
        }

        PostPropertyRecyclerViewAdapter postPropertyRecyclerViewAdapter=new PostPropertyRecyclerViewAdapter(PostPropertyActivity.this,postPropertyArrayList);
        post_recycler_view.setAdapter(postPropertyRecyclerViewAdapter);
    }
}
