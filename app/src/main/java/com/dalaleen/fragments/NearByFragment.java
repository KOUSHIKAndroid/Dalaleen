package com.dalaleen.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dalaleen.Activities.BaseActivity;
import com.dalaleen.R;
import com.dalaleen.adapters.NearByRecyclerViewAdapter;
import com.dalaleen.custome_front.LatoRegular;
import com.dalaleen.helper.NetworkConnection;
import com.dalaleen.Pojo.SetGetNearBy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by su on 3/30/17.
 */

public class NearByFragment extends Fragment implements OnMapReadyCallback {

    public  static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    RecyclerView recyclerView;

    ArrayList<SetGetNearBy> nearByArrayList;

    String checkingTAG;
    View MainV;
    MapView mMapView;

    View global_view,locationButton;


    public static NearByFragment getInstance(final String checkingTAG) {
        NearByFragment frag_ = new NearByFragment();
        frag_.checkingTAG = checkingTAG;
        return frag_;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainV = inflater.inflate(R.layout.fragment_location, container, false);
        return MainV;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




//        ***************** Header Control**********************************************

        ((LatoRegular)((BaseActivity)getActivity()).findViewById(R.id.toolbar_title)).setText(getString(R.string.title_nearby));

//        ******************** End Header Control ****************************




        global_view=view;

        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            // write your logic here
            if (new NetworkConnection(getActivity()).netCheck()) {

                mMapView.getMapAsync(this);
                createNearByListAndAdd();

            } else {
                view.findViewById(R.id.linear_layout_main).setVisibility(View.GONE);
                view.findViewById(R.id.linear_network_check_show).setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng mumbai = new LatLng(19.0760, 72.8777);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mumbai, 13));

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);


        locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        locationButton.setVisibility(View.GONE);

        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        googleMap.addMarker(new MarkerOptions()
                .title("mumbai")
                .snippet("The most populous city in India.")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.place_green))
                .position(mumbai));

        LatLng Kolkata = new LatLng(22.5726, 88.3639);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Kolkata, 4));

        googleMap.addMarker(new MarkerOptions()
                .title("kolkata")
                .snippet("The most populous city in India.")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.place_blue))
                .position(Kolkata));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_MULTIPLE_REQUEST:
                if (grantResults.length > 0) {
                    boolean cameraPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if(cameraPermission && readExternalFile)
                    {
                        // write your logic here
                        if (new NetworkConnection(getActivity()).netCheck()) {

                            mMapView.getMapAsync(this);
                            createNearByListAndAdd();

                        } else {
                            global_view.findViewById(R.id.linear_layout_main).setVisibility(View.GONE);
                            global_view.findViewById(R.id.linear_network_check_show).setVisibility(View.VISIBLE);
                        }

                    } else {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_MULTIPLE_REQUEST);
                        }
                    }
                }
                break;
        }
    }

    public void createNearByListAndAdd(){

       nearByArrayList=new ArrayList<>();

        for(int i=0;i<10;i++){

            SetGetNearBy setGetNearBy=new SetGetNearBy();
            setGetNearBy.setImg(""+ "drawable://" + R.drawable.image);
            setGetNearBy.setName("Royal Avenue"+i);
            setGetNearBy.setBhk(i+" BHK");
            setGetNearBy.setAddress("Rajarhat,New Town"+i);

            nearByArrayList.add(setGetNearBy);
        }

        NearByRecyclerViewAdapter nearByRecyclerViewAdapter=new NearByRecyclerViewAdapter(getActivity(),nearByArrayList);
        recyclerView.setAdapter(nearByRecyclerViewAdapter);

    }

    private void checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) +
                    ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {

                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            PERMISSIONS_MULTIPLE_REQUEST);

                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            PERMISSIONS_MULTIPLE_REQUEST);
                }
            }
            else {
                // write your logic here
                if (new NetworkConnection(getActivity()).netCheck()) {

                    mMapView.getMapAsync(this);
                    createNearByListAndAdd();

                } else {
                    global_view.findViewById(R.id.linear_layout_main).setVisibility(View.GONE);
                    global_view.findViewById(R.id.linear_network_check_show).setVisibility(View.VISIBLE);
                }
            }
        }
    }

}
