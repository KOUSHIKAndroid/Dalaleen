package com.dalaleen.fragments;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.dalaleen.Activities.BaseActivity;
import com.dalaleen.Pojo.Catagory;
import com.dalaleen.R;
import com.dalaleen.Activities.TakePlaceActivity;
import com.dalaleen.adapters.CatagoryPopupAdapter;
import com.dalaleen.custome_front.LatoBoldTV;
import com.dalaleen.custome_front.LatoRegular;
import com.dalaleen.helper.ApiConstant;
import com.dalaleen.Interface.CustomAsynctask;
import com.dalaleen.helper.RangeSeekBar;
import com.dalaleen.logger.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


/**
 * Created by su on 4/5/17.
 */

public class SearchFilterFragment extends Fragment  {

    RelativeLayout RLSearchByLocation;

    LatoRegular tv_search_next;
    LinearLayout linear_list_grid_parent_view;
    ImageView image_view_list_grid;

    Spinner spinner_types_of_properties;

    ArrayList<Catagory> categoryList;
    ArrayList<String> typesOfPropertiesList;

    ProgressDialog progDailog;

    RangeSeekBar<Long> seekBarBudget;
    RangeSeekBar<Long> seekBarSize;

    String LowPriceSize = "";
    String HighPriceSize = "";

    String LowPriceBudget = "";
    String HighPriceSizeBudget = "";

    LinearLayout linear_layout_seek_bar_budget,linear_layout_seek_bar_size;
    LatoRegular TXT_catagory;
    private static final int VERTICAL_ITEM_SPACE = 10;
    String checkingTAG;

    ArrayList<String> typeOfProperties;
    ArrayList<String> bedRoomList;
    RelativeLayout RLCategory;
    EditText edit_search;
    FragmentManager fragmentManager;
    LinearLayout linear_layout_number_of_bedrooms;
    CustomAsynctask customAsynctask;
    CatagoryPopupAdapter catagoryPopupAdapter;
    AlertDialog Dialogs;

    public static SearchFilterFragment getInstance(final String checkingTAG){
        SearchFilterFragment frag_=new SearchFilterFragment();
        frag_.checkingTAG = checkingTAG;
        return frag_;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_filter, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        ***************** Header Control**********************************************
        tv_search_next= (LatoRegular) ((BaseActivity)getActivity()).findViewById(R.id.tv_search_next);
        linear_list_grid_parent_view= (LinearLayout) ((BaseActivity)getActivity()).findViewById(R.id.linear_list_grid_parent_view);
        image_view_list_grid= (ImageView) ((BaseActivity)getActivity()).findViewById(R.id.image_view_list_grid);
        ((LatoRegular)((BaseActivity)getActivity()).findViewById(R.id.toolbar_title)).setText(getString(R.string.title_advsearch));

//        ******************** End Header Control ****************************




        fragmentManager= getActivity().getSupportFragmentManager();
        customAsynctask=new CustomAsynctask(getActivity(), progDailog);
        spinner_types_of_properties= (Spinner) view.findViewById(R.id.spinner_types_of_properties);
        typesOfPropertiesList=new ArrayList<>();

        categoryList=new ArrayList<>();

        progDailog= new ProgressDialog(getActivity());
        progDailog.setMessage("Loading...");

        progDailog.setCancelable(false);
        progDailog.setCanceledOnTouchOutside(false);

        edit_search= (EditText) view.findViewById(R.id.edit_search);
        TXT_catagory=(LatoRegular)view.findViewById(R.id.TXT_catagory);
        RLCategory=(RelativeLayout) view.findViewById(R.id.RLCategory);

        /////////////create run time number of bedrooms/////////////////////
        linear_layout_number_of_bedrooms= (LinearLayout) view.findViewById(R.id.linear_layout_number_of_bedrooms);
        createBedRoomList();
        createRuntimeNumberOfBedroomsViews(linear_layout_number_of_bedrooms,bedRoomList);
        ////////////////////////end////////////////////////////////////////


        ///////////////add  Range seekbars//////////////////////////
        linear_layout_seek_bar_budget= (LinearLayout) view.findViewById(R.id.linear_layout_seek_bar_budget);
        addSeekbarBudget(linear_layout_seek_bar_budget);

        linear_layout_seek_bar_size= (LinearLayout) view.findViewById(R.id.linear_layout_seek_bar_size);
        addSeekbarSize(linear_layout_seek_bar_size);
        ///////////////////////////////////////////////////////////


        customAsynctask.getResultListenerFromAsynctaskForGet(ApiConstant.BASE_URL + "/App_control/category_lists", new CustomAsynctask.onAPIResponse() {
            @Override
            public void onSuccess(String result) {
                try {
                    Logger.showInfo("@@ DATA","--"+result);
                    JSONObject jsonObject=new JSONObject(result);
                    if(jsonObject.getString("response").equalsIgnoreCase("TRUE"))
                    {
                        JSONArray array=jsonObject.getJSONArray("info");
                        for(int i=0;i<array.length();i++){
                            JSONObject object=array.getJSONObject(i);
                            Catagory catagory=new Catagory();
                            catagory.setIcon(object.getString("icon"));
                            catagory.setParent_category(object.getString("parent_category") );
                            catagory.setStatus(object.getString("status"));
                            catagory.setTitle_arb(object.getString("title_arb"));
                            catagory.setTitle_eng(object.getString("title_eng"));
                            catagory.setTitle_turk(object.getString("title_turk"));
                            categoryList.add(catagory);
                        }
                        catagoryPopupAdapter=new CatagoryPopupAdapter(SearchFilterFragment.this,getActivity(),categoryList);
                    }

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String Error) {
                Logger.showInfo("@@ DATA","--"+Error);

            }
        });

        RLCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder Abuilder=new AlertDialog.Builder(getActivity());
                View popview=getActivity().getLayoutInflater().inflate(R.layout.catagorylistpopup,null);
                RecyclerView recyclerView=(RecyclerView)popview.findViewById(R.id.LIST_catgory);
                ImageView Close=(ImageView)popview.findViewById(R.id.IMG_close);

                Close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialogs.dismiss();
                    }
                });
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(catagoryPopupAdapter);
                Abuilder.setView(popview);
                Dialogs=Abuilder.create();
                Dialogs.show();




            }
        });




        RLSearchByLocation= (RelativeLayout) view.findViewById(R.id.RLSearchByLocation);

        RLSearchByLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getActivity(), TakePlaceActivity.class);
                getActivity().startActivity(i);
            }
        });

    }


    public void createBedRoomList(){
        bedRoomList=new ArrayList<>();
        for(int i=0;i<10;i++){
            bedRoomList.add(i+" BHK");
        }
    }



    public void createRuntimeNumberOfBedroomsViews(LinearLayout mainLayout,ArrayList<String> bedRoomList){

        for (int i=0;i<bedRoomList.size();i++) {

            LatoBoldTV tv_bedRom = new LatoBoldTV(getActivity());
            tv_bedRom.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv_bedRom.getLayoutParams();
            params.setMargins(dpToPx(1), dpToPx(0), dpToPx(1), dpToPx(0));
            tv_bedRom.setLayoutParams(params);

            tv_bedRom.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tv_bedRom.setTypeface(null, Typeface.BOLD);


            mainLayout.addView(tv_bedRom);
            tv_bedRom.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.number_of_bedroom_border));
            tv_bedRom.setGravity(Gravity.CENTER);
            tv_bedRom.setPadding(dpToPx(15), dpToPx(15), dpToPx(15), dpToPx(15)); //substitute parameters for left, top, right, bottom
            tv_bedRom.setText(bedRoomList.get(i));
        }
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    public void addSeekbarBudget(LinearLayout linear_layout_seek_bar_budget){
        seekBarBudget = new RangeSeekBar<Long>(Long.parseLong("100"), Long.parseLong("1000"), getContext());
        linear_layout_seek_bar_budget.addView(seekBarBudget);
        seekBarBudget.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Long>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Long minValue, Long maxValue) {
                LowPriceBudget = minValue.toString();
                HighPriceSizeBudget = maxValue.toString();

                Logger.showInfo("LowPriceBudget",""+LowPriceBudget);
                Logger.showInfo("HighPriceSizeBudget",""+HighPriceSizeBudget);
            }

            @Override
            public void onRangeSeekBarValuesChanging(RangeSeekBar<?> bar, int minValue, int maxValue) {

            }
        });
    }
    public void addSeekbarSize(LinearLayout linear_layout_seek_bar_size){
        seekBarSize = new RangeSeekBar<Long>(Long.parseLong("100"), Long.parseLong("900"), getContext());
        linear_layout_seek_bar_size.addView(seekBarSize);

        seekBarSize.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Long>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Long minValue, Long maxValue) {
                LowPriceSize = minValue.toString();
                HighPriceSize = maxValue.toString();

                Logger.showInfo("LowPriceBudget",""+LowPriceSize);
                Logger.showInfo("HighPriceSizeBudget",""+HighPriceSize);
            }

            @Override
            public void onRangeSeekBarValuesChanging(RangeSeekBar<?> bar, int minValue, int maxValue) {

            }
        });

    }


    public void setCatagory(Catagory catagory) {
        TXT_catagory.setText(catagory.getTitle_eng());
        Dialogs.dismiss();
        String  url=""+ ApiConstant.BASE_URL+""+"/App_control/provide_category_details?parent_category="+catagory.getParent_category()+"&lang=en";
        progDailog.show();
        customAsynctask.getResultListenerFromAsynctaskForGet(url,new CustomAsynctask.onAPIResponse() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject categoryObject=new JSONObject(result);
                    JSONArray infoArray=categoryObject.getJSONArray("info");

                    typesOfPropertiesList.add("Category");
                    for(int i=0;i<infoArray.length();i++){
                        typesOfPropertiesList.add(infoArray.getString(i));
                    }
                    Logger.showInfo("categoryList Size",""+categoryList.size());


                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,typesOfPropertiesList){

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            LatoRegular tv = (LatoRegular) view;
                            if(position == 0){
                                // Set the hint text color gray
                                tv.setTextColor(Color.parseColor("#9B9B9B"));

                            }
                            else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                    spinner_types_of_properties.setAdapter(spinnerArrayAdapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progDailog.dismiss();
            }

            @Override
            public void onError(String Error) {
                progDailog.dismiss();
            }
        });









        spinner_types_of_properties.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position==0)
                {
                    ((LatoRegular) parent.getChildAt(0)).setTextColor(Color.parseColor("#9B9B9B"));
                }
                else
                {
                    ((LatoRegular) parent.getChildAt(0)).setTextColor(Color.BLACK);
                    // Notify the selected item text
                    Toast.makeText(getActivity(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
