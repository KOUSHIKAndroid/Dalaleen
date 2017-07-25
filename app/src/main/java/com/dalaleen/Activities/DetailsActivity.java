package com.dalaleen.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dalaleen.R;
import com.dalaleen.adapters.DetailsFragmentStatePagerAdapter;
import com.dalaleen.adapters.ImageAdapater;
import com.dalaleen.fragments.DetailsFragmentOfDetails;
import com.dalaleen.fragments.DrawingsFragmentOfDetails;
import com.dalaleen.fragments.InfoFragmentOfDetails;
import com.dalaleen.fragments.LocationFragmentOfDetails;
import com.dalaleen.helper.NetworkConnection;
import com.dalaleen.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * Created by su on 4/7/17.
 */

public class DetailsActivity extends AppCompatActivity {

    LinkedList<Fragment> fragmentList;
    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    String PAGEDATA = "";
    ViewPager Pro_image;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        PAGEDATA = getIntent().getStringExtra("DATA");

        //Adding toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Pro_image=(ViewPager) findViewById(R.id.image);

        try {
            JSONObject object=new JSONObject(PAGEDATA);
            JSONArray image=object.getJSONArray("property_image");
            ArrayList<String> images=new ArrayList<>();
            for(int i=0;i<image.length();i++)
            {
               images.add(image.getString(i));
            }


            Logger.showInfo("@@ DATA",""+PAGEDATA+"\n"+" SIZE-"+images.size());

            ImageAdapater myCustomPagerAdapter = new ImageAdapater(DetailsActivity.this, images);

            Pro_image.setAdapter(myCustomPagerAdapter);


        }catch (JSONException e)
        {

        }






        findViewById(R.id.back2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        if (new NetworkConnection(DetailsActivity.this).netCheck()) {
            //Initializing the tablayout
            tabLayout = (TabLayout) findViewById(R.id.tabLayout);

            //Adding the tabs using addTab() method

            tabLayout.addTab(tabLayout.newTab().setText("Info"));
            tabLayout.addTab(tabLayout.newTab().setText("Details"));
            tabLayout.addTab(tabLayout.newTab().setText("Drawings"));
            tabLayout.addTab(tabLayout.newTab().setText("Location"));

            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            //Initializing viewPager
            viewPager = (ViewPager) findViewById(R.id.view_pager);

            fragmentList = new LinkedList<>();


            fragmentList.add(InfoFragmentOfDetails.getInstance(PAGEDATA));
            fragmentList.add(DetailsFragmentOfDetails.getInstance(PAGEDATA));
            fragmentList.add( DrawingsFragmentOfDetails.getInstance(PAGEDATA));
            fragmentList.add(LocationFragmentOfDetails.getInstance(PAGEDATA));

            //Creating our pager adapter
            DetailsFragmentStatePagerAdapter adapter = new DetailsFragmentStatePagerAdapter(getSupportFragmentManager(), fragmentList);

            //Adding adapter to pager
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


            //Adding onTabSelectedListener to swipe views
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });


        } else {
            findViewById(R.id.view_pager).setVisibility(View.GONE);
            findViewById(R.id.linear_network_check_show).setVisibility(View.VISIBLE);
        }
    }
}
