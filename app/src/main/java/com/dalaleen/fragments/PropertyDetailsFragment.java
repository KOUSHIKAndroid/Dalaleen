package com.dalaleen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dalaleen.Activities.BaseActivity;
import com.dalaleen.R;
import com.dalaleen.custome_front.LatoRegular;

/**
 * Created by su on 4/13/17.
 */

public class PropertyDetailsFragment extends Fragment {
    LatoRegular tv_bottom;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_property_details,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_bottom= (LatoRegular) view.findViewById(R.id.tv_bottom);

        //        ((MyAddPropertyActivity) getActivity()).gotoNextScreen();

        tv_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        ((BaseActivity)getActivity()).changeViewpagerInEditFragment();
            }
        });

    }

    public void performvalidation() {
        tv_bottom.performClick();
    }
}
