package com.dalaleen.fragments;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dalaleen.R;
import com.dalaleen.adapters.LocationFragmentOfDetailsRecyclerViewAdapter;
import com.dalaleen.helper.CustomMapView;
import com.dalaleen.helper.DividerItemDecoration;
import com.dalaleen.Pojo.SetGetLocationFragmentOfDetails;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by su on 4/7/17.
 */

public class LocationFragmentOfDetails extends Fragment implements OnMapReadyCallback {
    public  static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    CustomMapView mMapView;
    String DATA;

    public static LocationFragmentOfDetails getInstance(String DATA) {

        LocationFragmentOfDetails frag=new LocationFragmentOfDetails();
        frag.DATA = DATA;
        return frag;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location_of_details, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (CustomMapView) view.findViewById(R.id.mapView);

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();
        mMapView.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if(getActivity().checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    &&getActivity(). checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                //   gps functions.
            }
        googleMap.setMyLocationEnabled(true);


        googleMap.getUiSettings().setMyLocationButtonEnabled(true);



        try {
            JSONObject LOCATION=new JSONObject(DATA).getJSONObject("location");
            LatLng PropLatLng = new LatLng(Double.parseDouble(LOCATION.getString("lat")), Double.parseDouble(LOCATION.getString("long")));

            googleMap.addMarker(new MarkerOptions()
//                .title("mumbai")
//                .snippet("The most populous city in India.")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.place_green))
                    .position(PropLatLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(PropLatLng, 14));
        }catch (JSONException e)
        {}



//        googleMap.addMarker(new MarkerOptions()
//                .title("kolkata")
//                .snippet("The most populous city in India.")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.place_blue))
//                .position(Kolkata));
    }
}
