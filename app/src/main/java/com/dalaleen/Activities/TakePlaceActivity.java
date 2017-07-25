package com.dalaleen.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dalaleen.R;
import com.dalaleen.application.ApplicationClass;
import com.dalaleen.helper.NetworkConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by su on 4/12/17.
 */

public class TakePlaceActivity extends AppCompatActivity {

    boolean LOcattionFlag = true;
    ArrayAdapter adapter;
    ArrayList<String> Address_Location;
    ListView listviewLocation;
    CardView Search;
    private EditText locationsearck;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_place);
        listviewLocation = (ListView) findViewById(R.id.listviewLocation);
        Search = (CardView) findViewById(R.id.Search);



        findViewById(R.id.back2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        locationsearck = (EditText)findViewById(R.id.locationsearck);
        locationsearck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (new NetworkConnection(TakePlaceActivity.this).netCheck() == true && locationsearck.isFocusable())
//                    GetFORLOCATIOnmap(charSequence);
                    fetch_LocationSuggession(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        listviewLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Place = (String) parent.getItemAtPosition(position);
                LOcattionFlag = false;
                locationsearck.setText(Place);
                adapter.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void fetch_LocationSuggession(final CharSequence url) {
        String URL = "";
        Address_Location = new ArrayList<>();
        try {
            URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + URLEncoder.encode( url.toString().trim(),"UTF-8") + "&types=geocode&language=en&key=AIzaSyBGpXT79jCe-ho9VZ9tkizFV5Y6Qvq7U2Y";

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("URL",""+URL);
        final JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray Array = jsonObject.getJSONArray("predictions");
                    for (int i = 0; i < Array.length(); i++) {
                        JSONObject id = Array.getJSONObject(i);
                        if (!Address_Location.contains(id.getString("description")))
                            Address_Location.add(id.getString("description"));
                    }
                    if(Address_Location.size()>0) {
                        ArrayAdapter adapter = new ArrayAdapter(TakePlaceActivity.this, R.layout.location_list_adapter, android.R.id.text1, Address_Location);
                        listviewLocation.setAdapter(adapter);
                        listviewLocation.setVisibility(View.VISIBLE);

                    }else {
                        listviewLocation.setVisibility(View.GONE);
                    }
                    ViewGroup.LayoutParams params = listviewLocation.getLayoutParams();
                    listviewLocation.setLayoutParams(params);
                    listviewLocation.requestLayout();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        ApplicationClass.getInstance().addToRequestQueue(req);

    }

}
