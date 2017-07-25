package com.dalaleen.fragments;

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
import com.dalaleen.adapters.SearchGridRecyclerViewAdapter;
import com.dalaleen.adapters.SearchListRecyclerViewAdapter;
import com.dalaleen.helper.VerticalSpaceItemDecoration;
import com.dalaleen.Pojo.SetGetSearchList;
import java.util.ArrayList;

/**
 * Created by su on 4/17/17.
 */

public class SearchListShowFragment extends Fragment {

    RecyclerView list_search_recycler_view,grid_search_recycler_view;
    private static final int VERTICAL_ITEM_SPACE = 2;

    ArrayList<SetGetSearchList> searchListArrayList;

    SearchListRecyclerViewAdapter searchListRecyclerViewAdapter;

    SearchGridRecyclerViewAdapter searchGridRecyclerViewAdapter;

    View MainV;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainV=inflater.inflate(R.layout.search_list_show_fragment,container,false);
        return MainV;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchListArrayList=new ArrayList<>();

        list_search_recycler_view= (RecyclerView)view.findViewById(R.id.list_search_recycler_view);
        list_search_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        list_search_recycler_view.setItemAnimator(new DefaultItemAnimator());
        list_search_recycler_view.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));


        grid_search_recycler_view= (RecyclerView)view.findViewById(R.id.grid_search_recycler_view);
        grid_search_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        grid_search_recycler_view.setItemAnimator(new DefaultItemAnimator());
        grid_search_recycler_view.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));

        createSearchList();

        list_search_recycler_view.setVisibility(View.VISIBLE);
        grid_search_recycler_view.setVisibility(View.GONE);


        searchListRecyclerViewAdapter=new SearchListRecyclerViewAdapter(getActivity(),searchListArrayList);
        list_search_recycler_view.setAdapter(searchListRecyclerViewAdapter);
    }

    public  void createSearchList(){
        for(int i=0;i<20;i++)
        {
            SetGetSearchList sg=new SetGetSearchList();
            sg.setImg("drawable://" + R.drawable.image);
            sg.setName("Royal Avenue "+i);
            sg.setRoom_detail("Rajarhat, 2BHK, 900 sq.ft "+i);
            sg.setIQU("IQU 25,000 "+i);
            sg.setPrice("1000"+i);
            searchListArrayList.add(sg);
        }
    }

    public void showAdapterListVise(){

        list_search_recycler_view.setVisibility(View.VISIBLE);
        grid_search_recycler_view.setVisibility(View.GONE);


        searchListRecyclerViewAdapter=new SearchListRecyclerViewAdapter(getActivity(),searchListArrayList);
        list_search_recycler_view.setAdapter(searchListRecyclerViewAdapter);
    }

    public void showAdapterGridVise(){

        list_search_recycler_view.setVisibility(View.GONE);
        grid_search_recycler_view.setVisibility(View.VISIBLE);

        searchGridRecyclerViewAdapter=new SearchGridRecyclerViewAdapter(getActivity(),searchListArrayList);
        grid_search_recycler_view.setAdapter(searchGridRecyclerViewAdapter);
    }
}
