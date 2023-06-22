package com.example.expensetracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class BalanceActivity extends AppCompatActivity {
    TextView in,ex,bal;
    Button sub;
    String sid,sname,sexpence,balance,sstatus,smessage;
    String url = config.baseurl+"balanceupdate.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        in=findViewById(R.id.in);
        ex=findViewById(R.id.ex);
        sub=findViewById(R.id.sub);
        bal=findViewById(R.id.bal);

        HashMap<String, String> user = new SessionManager(BalanceActivity.this).getUserDetails();

        sid = user.get("id");
        sname = user.get("income");
        in.setText("Income : "+sname);

        Intent intent=getIntent();
        sexpence=intent.getStringExtra("expense");
        ex.setText("Expense : "+sexpence);

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total();



            }
        });
    }

    private void total() {
        float intEnergy = Float.parseFloat(sname);
        System.out.println(intEnergy);

        float intExpense = Float.parseFloat(sexpence);
        System.out.println(intExpense);

        balance=String.valueOf(intEnergy-intExpense);
        System.out.println("Balance: "+ balance);
        bal.setText(balance);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(BalanceActivity.this, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    sstatus = jsonObject.getString("status");
                    smessage = jsonObject.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if ((sstatus).equals("0")) {
                    Toast.makeText(BalanceActivity.this, "failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BalanceActivity.this, "succes", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BalanceActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", sid);
                map.put("balance", balance);

                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    }

