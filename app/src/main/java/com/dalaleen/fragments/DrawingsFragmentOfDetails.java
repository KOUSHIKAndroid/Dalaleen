package com.dalaleen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dalaleen.R;
import com.dalaleen.adapters.DrawingsRecyclerViewAdapter;
import com.dalaleen.helper.GridDividerDecoration;
import com.dalaleen.Pojo.SetGetDrawing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by su on 4/7/17.
 */

public class DrawingsFragmentOfDetails extends Fragment {
    ArrayList<String > drawingArrayList;
        String PageDATA;

    public static DrawingsFragmentOfDetails getInstance(final String checkingTAG){
        DrawingsFragmentOfDetails frag_=new DrawingsFragmentOfDetails();
        frag_.PageDATA = checkingTAG;
        return frag_;
    }


    RecyclerView recyclerView;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drawings_of_details, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView= (RecyclerView) view.findViewById(R.id.recycler_view);


        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
//        recyclerView.addItemDecoration(new GridDividerDecoration(getActivity()));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        loadDrawingDataAndSetAdapter();
    }

    public void loadDrawingDataAndSetAdapter(){
        drawingArrayList=new ArrayList<>();

        try {
            JSONObject OBJ=new JSONObject(PageDATA);


            JSONArray ARR=OBJ.getJSONArray("drawing_image");

            for(int i=0;i<ARR.length();i++)
            {
                drawingArrayList.add(ARR.getString(i));
            }

            DrawingsRecyclerViewAdapter mAdapter=new DrawingsRecyclerViewAdapter(getActivity(),drawingArrayList);
            recyclerView.setAdapter(mAdapter);


        }catch (JSONException e)
        {

        }





    }
}
