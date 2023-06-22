package com.example.expensetracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Homeactivity extends AppCompatActivity {
    CardView c1,c2,c3,c4;
    String income;
    TextView in,total,sum;
    int su;
   // String url = config.baseurl+"totalexpense.php";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);
        c1=findViewById(R.id.h1);
        c2=findViewById(R.id.h2);
        c3=findViewById(R.id.h3);
        in=findViewById(R.id.balan);
//        total=findViewById(R.id.total);
//        sum=findViewById(R.id.sum);

        HashMap<String, String> user = new SessionManager(Homeactivity.this).getUserDetails();


        income = user.get("income");
        in.setText("Current income : "+income);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homeactivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homeactivity.this, IncomeAlltransactionListActivity.class);
                startActivity(intent);
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Homeactivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

//        total.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               totalexpense();
//            }
//        });

    }

//    private void totalexpense() {
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                     su = jsonObject.getInt("sum");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                if ("0".equals(sum)) {
//                    Toast.makeText(Homeactivity.this, "FAILED", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(Homeactivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
//                    sum.setText(String.valueOf(su));               }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(Homeactivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
//            }
//
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
////                map.put("name",sname);
////                map.put("email",semail);
////                map.put("phoneno",sphone_no);
////                map.put("gender",sgender);
////                map.put("district",sspinner);
////                map.put("income",sincome);
////                map.put("username",susername);
////                map.put("password",spassword);
//                return map;
//            }
//        };
//        RequestQueue requestQueue= Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }
    }
