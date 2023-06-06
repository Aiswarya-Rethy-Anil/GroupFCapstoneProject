package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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

public class IncomeAlltransactionListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
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
        fetchingdata();
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