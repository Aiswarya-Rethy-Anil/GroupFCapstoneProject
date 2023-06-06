package com.example.expensetracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.expensetracker.Homeactivity;
import com.example.expensetracker.R;
import com.example.expensetracker.SessionManager;
import com.example.expensetracker.config;
import com.example.expensetracker.registeractivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class loginactivity extends AppCompatActivity {
    EditText username,password;
    Button login;
    TextView signup;
    String susername,spassword,sstatus,smessage,sid,sName,sEmail,sPhoneno,sGender,sdistrict,sUsernme,sPassword;
    String url= config.baseurl+"login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        username=findViewById(R.id.logname);
        password=findViewById(R.id.logpass);
        login=findViewById(R.id.loglogin);
        signup=findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginactivity.this, registeractivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log();


            }
        });
    }

    private void log() {
        susername=username.getText().toString();
        spassword=password.getText().toString();


        if (TextUtils.isEmpty(susername)){
            username.setError("ENTER USERNAME");
            return;
        }
        if (TextUtils.isEmpty(spassword)){
            username.setError("ENTER PASSWORD");
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    sstatus = jsonObject.getString("status");
                    smessage = jsonObject.getString("message");
                    sid=jsonObject.getString("id");
                    sName=jsonObject.getString("name");
                    sEmail=jsonObject.getString("email");
                    sPhoneno=jsonObject.getString("phoneno");
                    sGender=jsonObject.getString("gender");
                    sdistrict=jsonObject.getString("district");
                    sUsernme=jsonObject.getString("username");
                    sPassword=jsonObject.getString("password");

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                if ("0".equals(sstatus)) {
                    Toast.makeText(loginactivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(loginactivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                    new SessionManager(loginactivity.this).createLoginSession(sid,sName,sEmail,sPhoneno,sGender,sdistrict,sUsernme,spassword);
                    Intent intent = new Intent(loginactivity.this, Homeactivity.class);
                    startActivity(intent);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginactivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("username",susername);
                map.put("password",spassword);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}