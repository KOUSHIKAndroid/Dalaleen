package com.dalaleen.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dalaleen.Activities.BaseActivity;
import com.dalaleen.CustomView.LoadingImageView;
import com.dalaleen.CustomView.LoadingTextView;
import com.dalaleen.HTTP_HELPER.HTTP_Get;
import com.dalaleen.Interface.CustomAsynctask;
import com.dalaleen.Interface.DialogHelper;
import com.dalaleen.Pojo.MyProperties;
import com.dalaleen.R;
import com.dalaleen.adapters.ExplorerFeaturePropertiesViewAdapter;
import com.dalaleen.adapters.ExplorerHotPropertiesViewAdapter;
import com.dalaleen.custome_front.LatoRegular;
import com.dalaleen.helper.ApiConstant;
import com.dalaleen.helper.NetworkConnection;
import com.dalaleen.helper.UserDATAGetting;
import com.dalaleen.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by su on 3/30/17.
 */

public class ExplorerFragment extends Fragment implements View.OnClickListener{
    String checkingTAG;
    EditText edit_search;
    RecyclerView REC_HOTLIST,REC_FEATURELIST;
    ArrayList<MyProperties> FeatureList;
    ArrayList<MyProperties> HotList;
    LinearLayout LL_LOADER_FEATURE;
    ExplorerHotPropertiesViewAdapter HotAdapter;
    ExplorerFeaturePropertiesViewAdapter FeatureAdapter;
    public static ExplorerFragment getInstance(final String checkingTAG){
        ExplorerFragment frag_=new ExplorerFragment();
        frag_.checkingTAG = checkingTAG;
        return frag_;
    }

    ImageView IMG_Up_Arrow;
    RelativeLayout RL_MAIN,RL_Edit_Main;
    LinearLayout LL_TOP_1,LL_TOP_1_1,LL_TOP_2;

    LatoRegular TXT_residential,TXT_RentType,TXT_PropertyType,TXT_Provinence,TXT_Neighbourhood;

    JSONArray CategoryList,PropertyTypeDetailsList,ProvinenceList,NeighbourhoodList;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_explorer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        IMG_Up_Arrow=(ImageView)view.findViewById(R.id.IMG_Up_Arrow);
        IMG_Up_Arrow.setVisibility(View.GONE);
        IMG_Up_Arrow.setOnClickListener(this);
        RL_MAIN=(RelativeLayout)view.findViewById(R.id.RL_MAIN);
        RL_MAIN.setOnClickListener(this);
        RL_Edit_Main=(RelativeLayout)view.findViewById(R.id.RL_Edit_Main);
        RL_Edit_Main.setVisibility(View.GONE);

        LL_TOP_1=(LinearLayout)view.findViewById(R.id.LL_TOP_1);
        LL_TOP_1.setVisibility(View.GONE);
        LL_TOP_1_1=(LinearLayout)view.findViewById(R.id.LL_TOP_1_1);
        LL_TOP_1_1.setVisibility(View.GONE);
        LL_TOP_2=(LinearLayout)view.findViewById(R.id.LL_TOP_2);
        LL_TOP_2.setVisibility(View.GONE);

        TXT_residential=(LatoRegular)view.findViewById(R.id.TXT_residential);
        TXT_RentType=(LatoRegular)view.findViewById(R.id.TXT_RentType);
        TXT_PropertyType=(LatoRegular)view.findViewById(R.id.TXT_PropertyType);
        TXT_Provinence=(LatoRegular)view.findViewById(R.id.TXT_Provinence);
        TXT_Neighbourhood=(LatoRegular)view.findViewById(R.id.TXT_Neighbourhood);
        TXT_residential.setOnClickListener(this);
        TXT_RentType.setOnClickListener(this);
        TXT_PropertyType.setOnClickListener(this);
        TXT_Provinence.setOnClickListener(this);
        TXT_Neighbourhood.setOnClickListener(this);




//        ***************** Header Control**********************************************

        ((LatoRegular)((BaseActivity)getActivity()).findViewById(R.id.toolbar_title)).setText(getString(R.string.title_explore));

//        ******************** End Header Control ****************************


        REC_HOTLIST=(RecyclerView)view.findViewById(R.id.REC_HOTLIST);
        REC_FEATURELIST=(RecyclerView)view.findViewById(R.id.REC_FEATURELIST);
        REC_HOTLIST.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        REC_FEATURELIST.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));


        new HTTP_Get(ApiConstant.CATEGORYLIST) {
            @Override
            protected void OnSucess(String Response) {
                try {
                    JSONObject jsonObject=new JSONObject(Response);
                    CategoryList=jsonObject.getJSONArray("info");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void OnErrorApi(String Error) {

            }

            @Override
            protected void OnHttPError(String HttpError) {

            }
        };


        new HTTP_Get(ApiConstant.CATEGORYLIST_PROVINENCE) {
            @Override
            protected void OnSucess(String Response) {
                try {
                    JSONObject jsonObject=new JSONObject(Response);
                    ProvinenceList=jsonObject.getJSONArray("info");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void OnErrorApi(String Error) {

            }

            @Override
            protected void OnHttPError(String HttpError) {

            }
        };







        FeatureList=new ArrayList<>();
        HotList=new ArrayList<>();


        if(new NetworkConnection(getActivity()).netCheck()){

            LoadHOTProperties(0);
            LoadFEATUREProperties(0);
            edit_search= (EditText) view.findViewById(R.id.edit_search);
            view.findViewById(R.id.img_clear).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edit_search.setText("");

                }
            });

        }else {
            view.findViewById(R.id.linear_layout_main).setVisibility(View.GONE);
            view.findViewById(R.id.linear_network_check_show).setVisibility(View.VISIBLE);
        }

    }

    public void LoadHOTProperties(final int position) {

        String URL= ApiConstant.BASE_URL+"/Jsonapp_control/hot_listing_property_list?lang=en&start_from="+position+"&per_page=5";

        new CustomAsynctask(getActivity(),new ProgressDialog(getActivity())).getResultListenerFromAsynctaskForGet(URL, new CustomAsynctask.onAPIResponse() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("response").equalsIgnoreCase("TRUE")) {
                        JSONArray array = object.getJSONArray("info");

                        if (HotAdapter != null && HotList.get(HotList.size() - 1) == null && position > 0) {
                            HotList.remove(HotList.size() - 1);
                            HotAdapter.notifyDataSetChanged();
                        }

                        for (int i = 0; i < array.length(); i++) {
                            MyProperties myProperties = new MyProperties();
                            JSONObject jsonObject = array.getJSONObject(i);
                            myProperties.setId(jsonObject.getString("id"));
                            myProperties.setName(jsonObject.getString("name"));
                            myProperties.setAddress(jsonObject.getString("address"));
                            myProperties.setCategory_name(jsonObject.getString("Category_name"));
                            myProperties.setProperty_description(jsonObject.getString("property_description"));
                            myProperties.setPrice(jsonObject.getString("Price"));
                            myProperties.setProperty_image(jsonObject.getString("property_image"));
                            myProperties.setCategory_sub(jsonObject.getString("category_sub"));
                            myProperties.setDetails(jsonObject.getJSONObject("details") + "");
                            HotList.add(myProperties);
                        }


                        if(object.getInt("start_from")>0)
                        {
                            HotList.add(null);
                        }
                        if (HotAdapter == null) {
                            HotAdapter = new ExplorerHotPropertiesViewAdapter(ExplorerFragment.this,getActivity(), HotList, object.getInt("start_from"));
                            REC_HOTLIST.setAdapter(HotAdapter);
                        } else {
                            HotAdapter.notifyDataSetChanged();
                        }
                    }

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String Error) {

            }
        });

    }

    public void LoadFEATUREProperties(final int position) {

        String URL= ApiConstant.BASE_URL+"/jsonapp_control/feature_property_list?lang=en&start_from="+position+"&per_page=5";
        Logger.showInfo("@@ FEATURE",""+URL);
        new CustomAsynctask(getActivity(),new ProgressDialog(getActivity())).getResultListenerFromAsynctaskForGet(URL, new CustomAsynctask.onAPIResponse() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("response").equalsIgnoreCase("TRUE")) {

                        if(FeatureAdapter!=null && FeatureList.get(FeatureList.size()-1)==null && position>0)
                        {
                            FeatureList.remove(FeatureList.size()-1);
                            FeatureAdapter.notifyDataSetChanged();
                        }



                        JSONArray array = object.getJSONArray("info");
                        for (int i = 0; i < array.length(); i++) {
                            MyProperties myProperties = new MyProperties();
                            JSONObject jsonObject = array.getJSONObject(i);
                            myProperties.setId(jsonObject.getString("id"));
                            myProperties.setName(jsonObject.getString("name"));
                            myProperties.setAddress(jsonObject.getString("address"));
                            myProperties.setCategory_name(jsonObject.getString("Category_name"));
                            myProperties.setProperty_description(jsonObject.getString("property_description"));
                            myProperties.setPrice(jsonObject.getString("Price"));
                            myProperties.setProperty_image(jsonObject.getString("property_image"));
                            myProperties.setCategory_sub(jsonObject.getString("category_sub"));
                            myProperties.setDetails(jsonObject.getJSONObject("details") + "");
                            FeatureList.add(myProperties);
                        }
                        if(object.getInt("start_from")>0)
                        {
                            FeatureList.add(null);
                        }


                        if (FeatureAdapter == null) {
                            FeatureAdapter = new ExplorerFeaturePropertiesViewAdapter(ExplorerFragment.this, getActivity(), FeatureList, object.getInt("start_from"));
                            REC_FEATURELIST.setAdapter(FeatureAdapter);
                        } else {
                            FeatureAdapter.notifyDataSetChanged();
                        }
//                        LL_LOADER_FEATURE.removeAllViews();

                    }
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String Error) {

            }
        });

    }


    private void LoderView(String hot) {
        View itemView=getActivity().getLayoutInflater().inflate(R.layout.explorer_list_row_loader,null);
        LoadingImageView img;
        LoadingTextView tv_name,tv_place;
        img= (LoadingImageView) itemView.findViewById(R.id.img);
        tv_name= (LoadingTextView) itemView.findViewById(R.id.tv_name);
        tv_place= (LoadingTextView) itemView.findViewById(R.id.tv_place);
        if (UserDATAGetting.isTablet(getActivity())) {
            LinearLayout.LayoutParams imglay = new LinearLayout.LayoutParams(UserDATAGetting.dpToPx(300, getActivity()), UserDATAGetting.dpToPx(300, getActivity()));
            img.setLayoutParams(imglay);
            Logger.showInfo("tablet", "" + UserDATAGetting.isTablet(getActivity()));
        } else {
            LinearLayout.LayoutParams imglay = new LinearLayout.LayoutParams(UserDATAGetting.dpToPx(150, getActivity()), UserDATAGetting.dpToPx(150, getActivity()));
            img.setLayoutParams(imglay);
            Logger.showInfo("tablet", "" + UserDATAGetting.isTablet(getActivity()));
        }
        img.startLoading();
        tv_name.startLoading();
        tv_place.startLoading();

        LL_LOADER_FEATURE.addView(itemView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.RL_MAIN:
                IMG_Up_Arrow.setVisibility(View.VISIBLE);
                RL_MAIN.setVisibility(View.GONE);
                RL_Edit_Main.setVisibility(View.VISIBLE);

                LL_TOP_1.setVisibility(View.VISIBLE);
                LL_TOP_1_1.setVisibility(View.VISIBLE);
                LL_TOP_2.setVisibility(View.VISIBLE);
                break;
            case R.id.IMG_Up_Arrow:
                LL_TOP_2.setVisibility(View.GONE);
                LL_TOP_1.setVisibility(View.GONE);
                LL_TOP_1_1.setVisibility(View.GONE);
                RL_Edit_Main.setVisibility(View.GONE);
                RL_MAIN.setVisibility(View.VISIBLE);
                IMG_Up_Arrow.setVisibility(View.GONE);
                break;
            case R.id.TXT_residential:

                new DialogHelper(getActivity()).SelectSimpleList(CategoryList, "title_eng", new DialogHelper.ListItemClick() {
                    @Override
                    public void OnItemSelct(String data) {
                        try {
                            TXT_residential.setText(new JSONObject(data).getString("title_eng"));
                            new HTTP_Get(ApiConstant.CATEGORYLIST_DETAILS+"parent_category="+new JSONObject(data).getString("category_id")) {
                                @Override
                                protected void OnSucess(String Response) {
                                    try {
                                        JSONObject jsonObject=new JSONObject(Response);
                                        PropertyTypeDetailsList=jsonObject.getJSONArray("info");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                protected void OnErrorApi(String Error) {

                                }

                                @Override
                                protected void OnHttPError(String HttpError) {

                                }
                            };
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void OnCancel() {

                    }
                });
                break;

            case R.id.TXT_RentType:
                try {
                 JSONArray   CategoryList_2=  new JSONArray("[{\"name\":\"All\"},{\"name\":\"Sell\"},{\"name\":\"Rent\"}]");
                    new DialogHelper(getActivity()).SelectSimpleList(CategoryList_2, "name", new DialogHelper.ListItemClick() {
                        @Override
                        public void OnItemSelct(String data) {
                            try {
                                TXT_RentType.setText(new JSONObject(data).getString("name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void OnCancel() {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                break;

            case R.id.TXT_PropertyType:
                if(PropertyTypeDetailsList.length()>0)
                new DialogHelper(getActivity()).SelectSimpleList(PropertyTypeDetailsList, "title_eng", new DialogHelper.ListItemClick() {
                    @Override
                    public void OnItemSelct(String data) {
                        try {
                            TXT_PropertyType.setText(new JSONObject(data).getString("title_eng"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void OnCancel() {

                    }
                });
                break;
            case R.id.TXT_Provinence:
                if(ProvinenceList.length()>0)
                    new DialogHelper(getActivity()).SelectSimpleList(ProvinenceList, "name", new DialogHelper.ListItemClick() {
                        @Override
                        public void OnItemSelct(String data) {
                            try {
                                TXT_Provinence.setText(new JSONObject(data).getString("name"));
                                NeighbourhoodList=new JSONObject(data).getJSONArray("province_info_neighbourhood");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void OnCancel() {

                        }
                    });
                break;
                case R.id.TXT_Neighbourhood:
                if(NeighbourhoodList.length()>0)
                    new DialogHelper(getActivity()).SelectSimpleList(NeighbourhoodList, "sub_province_name", new DialogHelper.ListItemClick() {
                        @Override
                        public void OnItemSelct(String data) {
                            try {
                                TXT_Neighbourhood.setText(new JSONObject(data).getString("sub_province_name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void OnCancel() {

                        }
                    });
                break;




        }

    }
}
