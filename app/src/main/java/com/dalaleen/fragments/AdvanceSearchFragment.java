package com.dalaleen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dalaleen.Activities.BaseActivity;
import com.dalaleen.R;
import com.dalaleen.custome_front.LatoRegular;
import com.dalaleen.helper.NetworkConnection;
import com.dalaleen.logger.Logger;

/**
 * Created by su on 3/30/17.
 */

public class AdvanceSearchFragment extends Fragment {

    FragmentManager fragmentManager;

    SearchListShowFragment searchListShowFragment=null;

    boolean isListViseView=true;

    LatoRegular tv_search_next;
    LinearLayout linear_list_grid_parent_view;
    ImageView image_view_list_grid;


    String checkingTAG;


    public static AdvanceSearchFragment getInstance(final String checkingTAG){
        AdvanceSearchFragment frag_=new AdvanceSearchFragment();
        frag_.checkingTAG = checkingTAG;
        return frag_;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_advance_search, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        ******************** Header Title ****************************************

        tv_search_next= (LatoRegular) ((BaseActivity)getActivity()).findViewById(R.id.tv_search_next);
        linear_list_grid_parent_view= (LinearLayout) ((BaseActivity)getActivity()).findViewById(R.id.linear_list_grid_parent_view);
        image_view_list_grid= (ImageView) ((BaseActivity)getActivity()).findViewById(R.id.image_view_list_grid);
        ((LatoRegular)((BaseActivity)getActivity()).findViewById(R.id.toolbar_title)).setText(getString(R.string.title_advsearch));

//      *********************** End Header Title*************************************


        fragmentManager= getActivity().getSupportFragmentManager();

        if(new NetworkConnection(getActivity()).netCheck()){

            tv_search_next.setVisibility(View.VISIBLE);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(
                    R.id.linear_layout_container,
                    SearchFilterFragment.getInstance(
                            "SearchFilterFragment"
                    ),
                    "SearchFilterFragment"
            );
            //int count = fragmentManager.getBackStackEntryCount();
            //fragmentTransaction.addToBackStack(String.valueOf(count));
            fragmentTransaction.commit();

        }else {

            view.findViewById(R.id.linear_layout_container).setVisibility(View.GONE);
            view.findViewById(R.id.linear_network_check_show).setVisibility(View.VISIBLE);
        }

        tv_search_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linear_list_grid_parent_view.setVisibility(View.VISIBLE);
                tv_search_next.setVisibility(View.GONE);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(
                        R.id.linear_layout_container,new SearchListShowFragment(),
                        "SearchListShowFragment"
                );

                fragmentTransaction.addToBackStack("SearchListShowFragment");
                fragmentTransaction.commit();
            }
        });



        image_view_list_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentFragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();

                searchListShowFragment= (SearchListShowFragment) fragmentManager.findFragmentByTag(currentFragmentTag);

                if(searchListShowFragment!=null){
                    Logger.showInfo("SearchListShowFragment:","found");
                }else {
                    Logger.showInfo("SearchListShowFragment:","not found");
                }


                if(isListViseView){
                    isListViseView=false;
                    image_view_list_grid.setImageResource(R.drawable.ic_format_list_bulleted_white_24dp);
                    searchListShowFragment.showAdapterGridVise();

                }else {
                    isListViseView=true;
                    image_view_list_grid.setImageResource(R.drawable.ic_thumbnail_white_24dp);
                    searchListShowFragment.showAdapterListVise();

                }

            }
        });

    }
}
