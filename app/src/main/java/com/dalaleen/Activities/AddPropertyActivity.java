package com.dalaleen.Activities;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.dalaleen.R;
import com.dalaleen.adapters.MyAddPropertyFragmentStatePagerAdapter;
import com.dalaleen.fragments.AddImagesFragment;
import com.dalaleen.fragments.AdditionalInformationFragment;
import com.dalaleen.fragments.PropertyDetailsFragment;

import java.util.LinkedList;

public class AddPropertyActivity extends AppCompatActivity implements View.OnClickListener{
    ViewPager view_pager;
    LinkedList<Fragment> fragmentList;
    LinearLayout linear_first_view,linear_second_view,linear_third_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        view_pager= (ViewPager) findViewById(R.id.view_pager);

        linear_first_view= (LinearLayout) findViewById(R.id.linear_first_view);
        linear_second_view= (LinearLayout) findViewById(R.id.linear_second_view);
        linear_third_view= (LinearLayout) findViewById(R.id.linear_third_view);


        fragmentList = new LinkedList<>();

        fragmentList.add(new PropertyDetailsFragment());
        fragmentList.add(new AddImagesFragment());
        fragmentList.add(new AdditionalInformationFragment());

        MyAddPropertyFragmentStatePagerAdapter adapter = new MyAddPropertyFragmentStatePagerAdapter(getSupportFragmentManager(), fragmentList);
        view_pager.setAdapter(adapter);
        view_pager.setCurrentItem(0);
        view_pager.setOffscreenPageLimit(1);

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        linear_first_view.setBackgroundColor(Color.parseColor("#F0A81B"));
                        linear_second_view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        linear_third_view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        break;
                    case 1:
                        linear_first_view.setBackgroundColor(Color.parseColor("#F0A81B"));
                        linear_second_view.setBackgroundColor(Color.parseColor("#F0A81B"));
                        linear_third_view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        break;
                    case 2:
                        linear_first_view.setBackgroundColor(Color.parseColor("#F0A81B"));
                        linear_second_view.setBackgroundColor(Color.parseColor("#F0A81B"));
                        linear_third_view.setBackgroundColor(Color.parseColor("#F0A81B"));
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void gotoNextScreen() {
        if (view_pager.getCurrentItem() == 0) {
            view_pager.setCurrentItem(1, true);
        } else if (view_pager.getCurrentItem() == 1) {
            view_pager.setCurrentItem(2, true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back2:
                onBackPressed();
                break;
        }
    }
}
