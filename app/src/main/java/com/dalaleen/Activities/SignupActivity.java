package com.dalaleen.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dalaleen.R;
import com.dalaleen.adapters.SignUpFragmentStatePagerAdapter;
import com.dalaleen.fragments.AgentSignupFragment;
import com.dalaleen.fragments.OwnerSignupFragment;
import com.dalaleen.helper.CustomAlertForBackground;
import com.dalaleen.helper.MyCustomAlertBackgroundListener;
import com.dalaleen.logger.Logger;
import java.util.LinkedList;

/**
 * Created by su on 3/29/17.
 */

public class SignupActivity extends AppCompatActivity implements MyCustomAlertBackgroundListener{
    ViewPager pager;
    LinkedList<Fragment> fragmentList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        pager = (ViewPager) findViewById(R.id.pager);
        fragmentList = new LinkedList<>();
        fragmentList.add(new OwnerSignupFragment());
        fragmentList.add(new AgentSignupFragment());

        SignUpFragmentStatePagerAdapter adapter = new SignUpFragmentStatePagerAdapter(getSupportFragmentManager(), fragmentList);
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);
        pager.setOffscreenPageLimit(1);
        ((View)findViewById(R.id.splitLine_agent_tab)).setBackgroundColor(Color.parseColor("#00000000"));
        ((View)findViewById(R.id.splitLine_owner_tab)).setBackgroundColor(Color.parseColor("#5493F2"));

        findViewById(R.id.linear_owner_tab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);
                ((View)findViewById(R.id.splitLine_agent_tab)).setBackgroundColor(Color.parseColor("#00000000"));
                ((View)findViewById(R.id.splitLine_owner_tab)).setBackgroundColor(Color.parseColor("#5493F2"));
            }
        });
        findViewById(R.id.linear_agent_tab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(1);
                ((View)findViewById(R.id.splitLine_agent_tab)).setBackgroundColor(Color.parseColor("#5493F2"));
                ((View)findViewById(R.id.splitLine_owner_tab)).setBackgroundColor(Color.parseColor("#00000000"));
            }
        });
    }


    @Override
    public void onBackPressed() {

        CustomAlertForBackground customAlertForBackground=new CustomAlertForBackground(SignupActivity.this,"Exit","Do you want to exit application?",this);
        customAlertForBackground.getListenerOKCancelFromNormalAlertBackground();
    }



    @Override
    public void callbackForBackgroundAlert(String result) {

        Logger.showInfo("result",""+result);

        if(result.equals("ok")){
            finish();
        }
    }
}
