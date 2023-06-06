package com.example.expensetracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class HistoryActivity extends AppCompatActivity {
    EditText name, email, phoneno, username;
    Button update;
    String sid,sname, semail, sphoneno, susername, sstatus, smessage;
    String url = config.baseurl + "update.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        name = findViewById(R.id.proname);
        email = findViewById(R.id.proemail);
        phoneno = findViewById(R.id.prophoneno);
        username = findViewById(R.id.prousername);
        update = findViewById(R.id.probutton);

        HashMap<String, String> user = new SessionManager(HistoryActivity.this).getUserDetails();

        sid = user.get("id");
        sname = user.get("name");
        name.setText(sname);
        semail = user.get("email");
        email.setText(semail);
        sphoneno = user.get("phoneno");
        phoneno.setText(sphoneno);
        susername = user.get("username");
        username.setText(susername);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

    }

    private void update() {
        sname = name.getText().toString();
        semail = email.getText().toString();
        sphoneno = phoneno.getText().toString();
        susername = username.getText().toString();

//        if (TextUtils.isEmpty(sname)) {
//            name.requestFocus();
//            name.setError("required field");
//            return;
//        }
//
//        if (TextUtils.isEmpty(semail)) {
//            email.requestFocus();
//            email.setError("required field");
//            return;
//        }
//
//        if (TextUtils.isEmpty(sphoneno)) {
//            phoneno.requestFocus();
//            phoneno.setError("required field");
//            return;
//        }
//
//        if (TextUtils.isEmpty(susername)) {
//            username.requestFocus();
//            username.setError("required field");
//            return;
//        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    sstatus = jsonObject.getString("status");
                    smessage = jsonObject.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if ((sstatus).equals("0")) {
                    Toast.makeText(HistoryActivity.this, "failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HistoryActivity.this, "succes", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HistoryActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", sid);
                map.put("name", sname);
                map.put("email", semail);
                map.put("phoneno", sphoneno);
                map.put("username", susername);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HistoryActivity.this);
        requestQueue.add(stringRequest);
    }

}