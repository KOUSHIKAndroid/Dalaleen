package com.dalaleen.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.dalaleen.R;
import com.dalaleen.custome_front.LatoLightTV;
import com.dalaleen.helper.CustomMapView;
import com.dalaleen.helper.NetworkConnection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by su on 4/7/17.
 */

public class DetailsFragmentOfDetails extends Fragment  {



    String checkingTAG;

    public static DetailsFragmentOfDetails getInstance(final String checkingTAG){
        DetailsFragmentOfDetails frag_=new DetailsFragmentOfDetails();
        frag_.checkingTAG = checkingTAG;
        return frag_;
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        try {
            JSONObject OBj=new JSONObject(checkingTAG);

            ((LatoLightTV)view.findViewById(R.id.TXT_Description)).setText(OBj.getString("details"));
        }catch (JSONException e){

        }


    }






}
