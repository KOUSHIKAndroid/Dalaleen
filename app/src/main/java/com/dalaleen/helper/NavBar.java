package com.dalaleen.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.dalaleen.Activities.BaseActivity;
import com.dalaleen.Activities.LoginActivity;
import com.dalaleen.Activities.MyFavoritesActivity;
import com.dalaleen.Activities.MyProfileActivity;
import com.dalaleen.Activities.MyPropertiesActivity;
import com.dalaleen.Activities.PostPropertyActivity;
import com.dalaleen.R;
import com.dalaleen.Activities.Wallet;
import com.dalaleen.application.ApplicationClass;
import com.dalaleen.custome_front.LatoRegular;
import com.dalaleen.custome_front.RobotoRegular;
import com.dalaleen.fragments.EditFragment;
import com.dalaleen.fragments.ExplorerFragment;
import com.dalaleen.fragments.NearByFragment;


/**
 * Created by bodhidipta on 05/12/16.
 */

public class NavBar implements NavigationView.OnNavigationItemSelectedListener{
    Context mcontext;
    DrawerLayout drawer;
    ImageView img_open_close_drawer;
    SharedPreferences shared;
    public LatoRegular toolbar_title;

    public NavBar(final Context context) {
        mcontext = context;
        drawer = (DrawerLayout) ((BaseActivity) context).findViewById(R.id.drawer_layout);



        img_open_close_drawer=(ImageView)((BaseActivity) context). findViewById(R.id.img_open_close_drawer);
        img_open_close_drawer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(drawer.isDrawerOpen(Gravity.LEFT))
                {
                    drawer.closeDrawer(Gravity.LEFT);
                }
                else
                {
                    drawer.openDrawer(Gravity.LEFT);
                }
            }
        });


        final NavigationView navigationView = (NavigationView) ((BaseActivity) context).findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        toolbar_title= (LatoRegular) ((BaseActivity)context).findViewById(R.id.toolbar_title);


//        ********************** Android Geoust User **************** Section

         shared = ApplicationClass.Loginsharedpreferences;
        if(shared.getString("user_id", "").equals("")) {
            navigationView.findViewById(R.id.linear_layout_logout).setVisibility(View.GONE);
            navigationView.findViewById(R.id.linear_layout_post_property).setVisibility(View.GONE);
            navigationView.findViewById(R.id.linear_layout_wallet).setVisibility(View.GONE);
            navigationView.findViewById(R.id.linear_layout_my_properties).setVisibility(View.GONE);
            navigationView.findViewById(R.id.linear_layout_my_favorites).setVisibility(View.GONE);
            navigationView.findViewById(R.id.linear_layout_my_saved_members).setVisibility(View.GONE);
        }










        navigationView.findViewById(R.id.linear_layout_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = ApplicationClass.Loginsharedpreferences.edit();
                editor.putString("user_id", "");
                editor.commit();

                drawer.closeDrawer(GravityCompat.START);
                Intent intent=new Intent(context, LoginActivity.class);
                context.startActivity(intent);

                ((BaseActivity) context).finish();

            }
        });


        navigationView.findViewById(R.id.linear_layout_post_property).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    drawer.closeDrawer(GravityCompat.START);
                    Intent intent = new Intent(context, PostPropertyActivity.class);
                    context.startActivity(intent);

            }
        });

        navigationView.findViewById(R.id.linear_layout_wallet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawer.closeDrawer(GravityCompat.START);
                RobotoRegular walet=(RobotoRegular)navigationView.findViewById(R.id.txt_wallet);

                Intent intent=new Intent(context,Wallet.class);
                Pair<View, String> p1 = Pair.create((View)walet, "Wallettitle");
                Pair<View, String> p2 = Pair.create((View)navigationView.findViewById(R.id.walet_amunt), "walletAmount");
                ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation((BaseActivity)context,p1,p2);
                context.startActivity(intent,activityOptionsCompat.toBundle());

            }
        });

        navigationView.findViewById(R.id.linear_layout_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(!shared.getString("user_id", "").equals("")) {
                drawer.closeDrawer(GravityCompat.START);
                Intent intent=new Intent(context,MyProfileActivity.class);
                context.startActivity(intent);
            }else {
//                    **************** Login Check Here

                Intent intent=new Intent(context,LoginActivity.class);
                context.startActivity(intent);
                ((BaseActivity) context).finish();



            }


            }
        });



        navigationView.findViewById(R.id.linear_layout_browse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawer.closeDrawer(GravityCompat.START);


                ((BaseActivity)context).bottomNavigationView.setSelectedItemId(R.id.action_explore);



            }
        });



        navigationView.findViewById(R.id.linear_layout_near_by).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawer.closeDrawer(GravityCompat.START);

                ((BaseActivity)context).bottomNavigationView.setSelectedItemId(R.id.action_nearby);

            }
        });

        navigationView.findViewById(R.id.linear_layout_advance_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawer.closeDrawer(GravityCompat.START);
                ((BaseActivity)context).bottomNavigationView.setSelectedItemId(R.id.action_search);

            }
        });


        navigationView.findViewById(R.id.linear_layout_my_properties).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawer.closeDrawer(GravityCompat.START);

                Intent intent=new Intent(context, MyPropertiesActivity.class);
                context.startActivity(intent);

            }
        });

        navigationView.findViewById(R.id.linear_layout_add_property).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               drawer.closeDrawer(GravityCompat.START);
                ((BaseActivity)context).bottomNavigationView.setSelectedItemId(R.id.action_addproperty);
            }
        });

        navigationView.findViewById(R.id.linear_layout_my_favorites).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawer(GravityCompat.START);
                Intent intent=new Intent(context, MyFavoritesActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void closeDrawer() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });
    }

    public void openDrawer() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (!drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    public boolean getDrawerStutus() {
        return drawer.isDrawerOpen(GravityCompat.START);
    }

}
