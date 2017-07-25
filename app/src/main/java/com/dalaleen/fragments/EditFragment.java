package com.dalaleen.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.dalaleen.R;
import com.dalaleen.adapters.MyAddPropertyFragmentStatePagerAdapter;
import com.dalaleen.helper.NetworkConnection;
import java.util.LinkedList;

/**
 * Created by su on 3/30/17.
 */

public class EditFragment extends Fragment{

    ViewPager view_pager;
    LinearLayout linear_first_view,linear_second_view,linear_third_view;
    LinkedList<Fragment> fragmentList;


    String checkingTAG;
    View MainV;
    public static EditFragment getInstance(final String checkingTAG){
        EditFragment frag_=new EditFragment();
        frag_.checkingTAG = checkingTAG;
        return frag_;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainV = inflater.inflate(R.layout.fragment_edit, container, false);
        return MainV;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view_pager= (ViewPager) view.findViewById(R.id.view_pager);

        linear_first_view= (LinearLayout) view.findViewById(R.id.linear_first_view);
        linear_second_view= (LinearLayout)view. findViewById(R.id.linear_second_view);
        linear_third_view= (LinearLayout) view.findViewById(R.id.linear_third_view);


        fragmentList = new LinkedList<>();

        fragmentList.add(new PropertyDetailsFragment());
        fragmentList.add(new AddImagesFragment());
        fragmentList.add(new AdditionalInformationFragment());


        if(new NetworkConnection(getActivity()).netCheck()){
            ///////////if network available then work here ///////////////////

        }else {
            view.findViewById(R.id.linear_layout_main).setVisibility(View.GONE);
            view.findViewById(R.id.linear_network_check_show).setVisibility(View.VISIBLE);
        }


        MyAddPropertyFragmentStatePagerAdapter adapter = new MyAddPropertyFragmentStatePagerAdapter((getActivity()).getSupportFragmentManager(), fragmentList);
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

    public void goto1stScreen() {
        view_pager.setCurrentItem(0, true);
        ((PropertyDetailsFragment)fragmentList.get(0)).performvalidation();
    }

    public void goto2ndScreen() {
        view_pager.setCurrentItem(1, true);
        ((AddImagesFragment)fragmentList.get(1)).performvalidation();
    }
}
