package com.dalaleen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dalaleen.R;
import com.dalaleen.custome_front.LatoBoldTV;
import com.dalaleen.custome_front.LatoLightTV;
import com.dalaleen.custome_front.LatoRegular;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by su on 4/7/17.
 */

public class InfoFragmentOfDetails extends Fragment {

    String PageData="";
    LinearLayout LL_Info;

    public static InfoFragmentOfDetails getInstance(final String checkingTAG){
        InfoFragmentOfDetails frag_=new InfoFragmentOfDetails();
        frag_.PageData = checkingTAG;
        return frag_;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            LL_Info=(LinearLayout)view.findViewById(R.id.LL_Info);
            JSONObject Obj=new JSONObject(PageData);

            ((LatoBoldTV)view.findViewById(R.id.TXT_PropertyName)).setText(Obj.getString("property_name"));
            ((LatoRegular)view.findViewById(R.id.Prop_Location)).setText(Obj.getString("property_address"));
            ((LatoBoldTV)view.findViewById(R.id.TXT_Price)).setText(Obj.getString("Price"));
            ((LatoBoldTV)view.findViewById(R.id.TXT_catagory)).setText(Obj.getString("Category"));
            ((LatoBoldTV)view.findViewById(R.id.TXT_subcat)).setText(Obj.getString("Sub Category"));

            JSONArray ARRY=Obj.getJSONArray("info");

            for(int i=0;i<ARRY.length();i++)
            {
                View RowView=getActivity().getLayoutInflater().inflate(R.layout.info_row,null);

                ((LatoBoldTV)RowView.findViewById(R.id.TXT_Name)).setText(ARRY.getJSONObject(i).getString("name"));
                ((LatoLightTV)RowView.findViewById(R.id.TXT_Value)).setText(ARRY.getJSONObject(i).getString("value"));
                LL_Info.addView(RowView);

            }


        }catch (JSONException e)
        {

        }



    }
}