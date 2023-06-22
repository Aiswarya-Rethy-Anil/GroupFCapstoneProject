package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.Collections;
import java.util.Comparator;

public class IncomeAlltransactionListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText search;

    ImageView view;
    Spinner category;
    String []scategory={"FILTER","Trip","Food","Hospital","Profits","Other"};
    String sscategory;
    String url =config.baseurl+"incomealltransactionlist.php";
    ProgressBar progressBar;
    Incomealltransactionadapter alltransactionadapter;
    ArrayList<Incomealltransactionmodel> alltransactionmodelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_alltransaction_list);

        recyclerView=findViewById(R.id.recycle);
        progressBar=findViewById(R.id.pbar);
        search=findViewById(R.id.search);
        category=findViewById(R.id.filter);
        view=findViewById(R.id.arrow);
        Button sortButton = findViewById(R.id.sortButton);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vi();

            }
        });
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IncomeAlltransactionListActivity.this,SortActivity.class);
                startActivity(intent);
            }
        });
//        sortButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Sort the list using a Comparator
//                Collections.sort(alltransactionmodelArrayList, new Comparator<Incomealltransactionmodel>() {
//                    @Override
//                    public int compare(Incomealltransactionmodel model1, Incomealltransactionmodel model2) {
//                        double amount1 = Double.parseDouble(model1.getPrice());
//                        double amount2 = Double.parseDouble(model2.getPrice());
//                        return Double.compare(amount1, amount2);
//                    }
//                });
//
//                // Notify the adapter or update the UI with the sorted list
//                // adapter.notifyDataSetChanged(); or update UI components
//            }
//        });
        ArrayAdapter adapter=new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,scategory);
        category.setAdapter(adapter);
//        filter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(IncomeAlltransactionListActivity.this,FilteringActivity.class);
//                startActivity(intent);
//            }
//        });
        search.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable text) {
                //new array list that will hold the filtered data
                ArrayList<Incomealltransactionmodel> filteredSongs = new ArrayList<>();

                if (alltransactionmodelArrayList != null && !alltransactionmodelArrayList.isEmpty()) {
                    //looping through existing elements
                    for (Incomealltransactionmodel  s: alltransactionmodelArrayList) {
                        //if the existing elements contains the search input
                        if (s.getDate().toLowerCase().contains(text.toString().toLowerCase())) {
                            //adding the element to filtered list
                            filteredSongs.add(s);
                        }
                    }
                }

                if (alltransactionadapter != null) {
                    //calling a method of the adapter class and passing the filtered list
                    alltransactionadapter.filterList(filteredSongs);
                }
            }
        });




        fetchingdata();
    }

    private void vi() {
        sscategory=category.getSelectedItem().toString();
        Intent intent=new Intent(IncomeAlltransactionListActivity.this,FilteringActivity.class);
        intent.putExtra("category",sscategory);
        startActivity(intent);
    }


    private void fetchingdata() {



        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("list>>",response);
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
                Toast.makeText(IncomeAlltransactionListActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue ex = Volley.newRequestQueue(IncomeAlltransactionListActivity.this);
        ex.add(stringRequest);
    }

    private void setupRecycler() {

        alltransactionadapter=new Incomealltransactionadapter(IncomeAlltransactionListActivity.this,alltransactionmodelArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(alltransactionadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(IncomeAlltransactionListActivity.this,RecyclerView.VERTICAL,false));
    }


}