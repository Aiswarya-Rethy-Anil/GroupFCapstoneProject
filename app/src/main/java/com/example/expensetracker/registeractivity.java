package com.example.expensetracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.expensetracker.R;
import com.example.expensetracker.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class registeractivity extends AppCompatActivity {
    EditText name,email,phone_no,username,password;
    RadioGroup gender;
    Button register;
    Spinner spinner;
    String sname,semail,sphone_no,susername,spassword,sgender,sspinner,sstatus,smessage;
    String []dis={"Select District","Kazarakod","Kannur","Wayanad","Kozhikkod"};
    String url = config.baseurl+"register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeractivity);
        name=findViewById(R.id.regname);
        email=findViewById(R.id.regmail);
        phone_no=findViewById(R.id.regphone);
        username=findViewById(R.id.reguser);
        password=findViewById(R.id.regpass);
        gender=findViewById(R.id.reggender);
        register=findViewById(R.id.regregister);
        spinner=findViewById(R.id.regspinner);

        ArrayAdapter adapter=new ArrayAdapter(registeractivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dis);
        spinner.setAdapter(adapter);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reg();
            }
        });

    }

    private void reg() {
        sname=name.getText().toString();
        semail=email.getText().toString();
        sphone_no=phone_no.getText().toString();
        susername=username.getText().toString();
        spassword=password.getText().toString();
        sspinner=spinner.getSelectedItem().toString();

        int id=gender.getCheckedRadioButtonId();
        RadioButton radioButton = gender.findViewById(id);
        sgender = radioButton.getText().toString();

        if (TextUtils.isEmpty(sname)){
            name.setError("ENTER NAME");
            name.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(semail)){
            email.setError("ENTER EMAIL");
            email.requestFocus();
            return;
        }
        if(!isEmailValid(semail))
        {
            email.setError("Invalid email");
            email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(sphone_no)){
            phone_no.setError("ENTER PHONE NO");
            phone_no.requestFocus();
            return;
        }
        if(!isPhoneValid(sphone_no))
        {
            phone_no.setError("Invalid phone number");
            phone_no.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(susername)){
            username.setError("ENTER USERNAME");
            username.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(spassword)){
            password.setError("ENTER PASSEWORD");
            password.requestFocus();
            return;
        }
        if(spassword.length()<6){
            password.setError("Enter minimum six characters");
            return;
        }
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
                if ("0".equals(sstatus)) {
                    Toast.makeText(registeractivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(registeractivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(registeractivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
            }

        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name",sname);
                map.put("email",semail);
                map.put("phoneno",sphone_no);
                map.put("gender",sgender);
                map.put("district",sspinner);
                map.put("username",susername);
                map.put("password",spassword);
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public static boolean isPhoneValid(String s) {
        Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }
}
