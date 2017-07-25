package com.dalaleen.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dalaleen.R;
import com.dalaleen.custome_front.LatoRegular;
import com.dalaleen.fragments.AdvanceSearchFragment;
import com.dalaleen.fragments.EditFragment;
import com.dalaleen.fragments.ExplorerFragment;
import com.dalaleen.fragments.NearByFragment;
import com.dalaleen.fragments.SearchFilterFragment;
import com.dalaleen.helper.CustomAlertForBackground;
import com.dalaleen.helper.MyCustomAlertBackgroundListener;
import com.dalaleen.helper.NavBar;
import com.dalaleen.logger.Logger;

import static com.dalaleen.R.drawable.ic_edit_blue_24dp;
import static com.dalaleen.R.drawable.ic_explore_blue_24dp;
import static com.dalaleen.R.drawable.ic_location_blue_24dp;
import static com.dalaleen.R.drawable.ic_search_blue_menu_24dp;

/**
 * Created by su on 3/29/17.
 */

public class BaseActivity extends AppCompatActivity implements MyCustomAlertBackgroundListener {

    public EditFragment editFragment;
    LatoRegular toolbar_title;

    public LinearLayout linear_list_grid_parent_view;
    public LatoRegular tv_search_next;
   public BottomNavigationView bottomNavigationView;
    public FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    NavBar navigationBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer_location_connect_edit);



         bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        tv_search_next= (LatoRegular) findViewById(R.id.tv_search_next);
        linear_list_grid_parent_view= (LinearLayout) findViewById(R.id.linear_list_grid_parent_view);




        toolbar_title= (LatoRegular) findViewById(R.id.toolbar_title);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.action_explore:
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(
                                        R.id.linear_layout_fragment_container,
                                        ExplorerFragment.getInstance(
                                                "ExplorerFragment"
                                        ),
                                        "ExplorerFragment"
                                );
                                fragmentTransaction.commit();
                                Logger.showInfo("@@ PAGE", " Explore");

                                break;

                            case R.id.action_nearby:
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(
                                        R.id.linear_layout_fragment_container,
                                        NearByFragment.getInstance(
                                                "NearByFragment"
                                        ),
                                        "NearByFragment"
                                );
                                fragmentTransaction.commit();
                                Logger.showInfo("@@ PAGE", " Near by");
                                break;
                            case R.id.action_search:

                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(
                                        R.id.linear_layout_fragment_container,
                                        SearchFilterFragment.getInstance(
                                                "SearchFilterFragment"
                                        ),
                                        "SearchFilterFragment"
                                );
                                fragmentTransaction.commit();
                                Logger.showInfo("@@ PAGE", "  Search");
                                break;
                            case R.id.action_addproperty:

                                startActivity(new Intent(BaseActivity.this,AddPropertyActivity.class));

//                                fragmentTransaction = fragmentManager.beginTransaction();
//
//                                editFragment = EditFragment.getInstance("EditFragment");
//
//
//                                fragmentTransaction.replace(
//                                        R.id.linear_layout_fragment_container, editFragment,
//                                        "EditFragment"
//                                );
//                                fragmentTransaction.commit();
                                Logger.showInfo("@@ PAGE", " Add Property");
//                                break;
                        }
                        return true;
                    }
                });


        fragmentTransaction.replace(
                R.id.linear_layout_fragment_container,
                ExplorerFragment.getInstance(
                        "ExplorerFragment"
                ),
                "ExplorerFragment"
        );
        fragmentTransaction.commit();




        navigationBar = new NavBar(BaseActivity.this) {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                return super.onNavigationItemSelected(item);
            }
        };


        fragmentManager= getSupportFragmentManager();








    }


    @Override
    public void onBackPressed() {
        CustomAlertForBackground customAlertForBackground=new CustomAlertForBackground(BaseActivity.this,"Exit","Do you want to exit application?",this);
        customAlertForBackground.getListenerOKCancelFromNormalAlertBackground();
    }


    @Override
    public void callbackForBackgroundAlert(String result) {
        Logger.showInfo("result",""+result);

        if(result.equals("ok")){
            finish();
        }
    }


    public void changeViewpagerInEditFragment(){
        if(editFragment!=null)
        editFragment.gotoNextScreen();
    }
}
