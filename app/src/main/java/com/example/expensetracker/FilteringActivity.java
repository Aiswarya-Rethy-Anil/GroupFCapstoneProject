package com.example.expensetracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FilteringActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText search;
    Button filter;
    String sscategory;
    String url =config.baseurl+"filter.php";
    ProgressBar progressBar;

    Filteradapter alltransactionadapter;
    ArrayList<Incomealltransactionmodel> alltransactionmodelArrayList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtering);
        Intent intent=getIntent();
        sscategory=intent.getStringExtra("category");
        Toast.makeText(this, sscategory, Toast.LENGTH_SHORT).show();
        recyclerView=findViewById(R.id.recycle1);
        progressBar=findViewById(R.id.pbar1);

//        search=findViewById(R.id.search);
//        filter=findViewById(R.id.filter);

//        filter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(FilteringActivity.this,FilteringActivity.class);
//                startActivity(intent);
//            }
//        });
//        search.addTextChangedListener(new TextWatcher() {
//            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
//            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
//            @Override
//            public void afterTextChanged(Editable text) {
//                //new array list that will hold the filtered data
//                ArrayList<Incomealltransactionmodel> filteredSongs = new ArrayList<>();
//
//                if (alltransactionmodelArrayList != null && !alltransactionmodelArrayList.isEmpty()) {
//                    //looping through existing elements
//                    for (Incomealltransactionmodel  s: alltransactionmodelArrayList) {
//                        //if the existing elements contains the search input
//                        if (s.getDate().toLowerCase().contains(text.toString().toLowerCase())) {
//                            //adding the element to filtered list
//                            filteredSongs.add(s);
//                        }
//                    }
//                }
//
//                if (alltransactionadapter != null) {
//                    //calling a method of the adapter class and passing the filtered list
//                    alltransactionadapter.filterList(filteredSongs);
//                }
//            }
//        });




        fetchingdata();
    }


    private void fetchingdata() {


        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("list>>",response);
                //Toast.makeText(FilteringActivity.this, response, Toast.LENGTH_SHORT).show();
                try {
                    progressBar.setVisibility(View.GONE);
                    alltransactionmodelArrayList = new ArrayList<>();
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        alltransactionmodelArrayList.add(new Incomealltransactionmodel(
                                jsonObject.getString("Id"),
                                jsonObject.getString("Price"),
                                jsonObject.getString("Category"),
                                jsonObject.getString("Date"),
                                jsonObject.getString("Discription"),
                                jsonObject.getString("Paymentmode"),
                                jsonObject.getString("Image")

                        ));
                        setupRecycler();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(FilteringActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("category",sscategory);


                return map;
            }
        };



        RequestQueue ex = Volley.newRequestQueue(FilteringActivity.this);
        ex.add(stringRequest);
    }

    private void setupRecycler() {

        alltransactionadapter=new Filteradapter(FilteringActivity.this,alltransactionmodelArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(alltransactionadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FilteringActivity.this,RecyclerView.VERTICAL,false));
    }


}