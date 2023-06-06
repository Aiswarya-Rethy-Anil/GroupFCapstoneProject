package com.example.expensetracker;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class AddActivity extends AppCompatActivity {

    EditText price,discription;
    Spinner category,paymentmode;
    Button submit,back,date;

    TextView expense;
    Calendar mycalendar;
    String sprice,sdate,sdiscription,spicture,sscategory,sspaymentmode,sstatus,smessage;
    String []scategory={"Select Category","Trip","Food","Hospital","Profits","Other"};
    String []spaymentmode={"Select paymentmode","Cash","Check","Bank Transfer","Googlepay"};
    String url = config.baseurl+"addexpense.php";

    private RequestQueue rQueue;
    private static ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        price=findViewById(R.id.price);
        date=findViewById(R.id.adate);
        discription=findViewById(R.id.dis);
//        expense=findViewById(R.id.ex1);
        category=findViewById(R.id.cat);
        paymentmode=findViewById(R.id.pay);
        submit=findViewById(R.id.button);
        back=findViewById(R.id.bac);


        ArrayAdapter adapter=new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,scategory);
        category.setAdapter(adapter);

        ArrayAdapter adapter1=new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,spaymentmode);
        paymentmode.setAdapter(adapter1);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                DatePickerDialog datePicker = new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int day) {
                                date.setText(day + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePicker.show();

            }
        });
//        expense.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AddActivity.this,AddExpenseActivity.class);
//                startActivity(intent);
//            }
//        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this,Homeactivity.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sub();
            }
        });
    }


    private void sub() {
        sscategory=category.getSelectedItem().toString();
        sspaymentmode=paymentmode.getSelectedItem().toString();
        sprice=price.getText().toString();
        sdate=date.getText().toString();
        sdiscription=discription.getText().toString();

        if (TextUtils.isEmpty(sprice)){
            price.setError("ENTER PRICE");
            price.requestFocus();
            return;
        }


        if (sscategory.equals("Select Category")){
            Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();
        }

        if (sspaymentmode.equals("Select Paymentmode")){
            Toast.makeText(this, "Select Paymentmode", Toast.LENGTH_SHORT).show();
        }




        if (TextUtils.isEmpty(sdate)){
            date.setError("ENTER THE DATE");
            date.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(sdiscription)){
            discription.setError("ENTER DISCRIPTION");
            discription.requestFocus();
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
                    Toast.makeText(AddActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
            }

        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Price", sprice);
                map.put("Category",sscategory);
                map.put("Date",sdate);
                map.put("Discription",sdiscription);
                map.put("Paymentmode",sspaymentmode);

                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

//        Intent intent=new Intent();
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        startActivityForResult(intent, 1);
//    }
//
//
//    @SuppressLint("Range")
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            // Get the Uri of the selected file
//            Uri uri = data.getData();
//            String uriString = uri.toString();
//            File myFile = new File(uriString);
//            String path = myFile.getAbsolutePath();
//            String displayName = null;
//            if (uriString.startsWith("content://")) {
//                Cursor cursor = null;
//                try {
//                    cursor =getContentResolver().query(uri, null, null, null, null);
//                    if (cursor != null && cursor.moveToFirst()) {
//                        displayName = cursor.getString(cursor.getColumnIndex( OpenableColumns.DISPLAY_NAME));
//                        Log.d("name ",displayName);
//
//                        uploadPDF(displayName,uri);
//                    }
//                } finally {
//                    cursor.close();
//                }
//            } else if (uriString.startsWith("file://")) {
//                displayName = myFile.getName();
//                Log.d("nameeeee>>>>  ",displayName);
//            }
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//    }
//
//    private void uploadPDF(final String pdfname, Uri pdffile) {
//        InputStream iStream = null;
//        try {
//
//            iStream = getContentResolver().openInputStream(pdffile);
//            final byte[] inputData = getBytes(iStream);
//
//            showSimpleProgressDialog(getApplicationContext(), null, "Uploading image", false);
//
//            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest( Request.Method.POST, url,
//                    new Response.Listener<NetworkResponse>() {
//                        @Override
//                        public void onResponse(NetworkResponse response) {
//                            removeSimpleProgressDialog();
//                            Log.d("res",new String(response.data));
//                            rQueue.getCache().clear();
//                            try {
//
//                                JSONObject jsonObject = new JSONObject(new String(response.data));
//
//                                jsonObject.toString().replace("\\\\","");
//
//                                sstatus = jsonObject.getString("status");
//                                smessage = jsonObject.getString("message");
//
//                                if (sstatus.equals("1")) {
//                                    Toast.makeText(getApplicationContext(), smessage, Toast.LENGTH_SHORT).show();
//
//                                }
//                                else {
//                                    Toast.makeText(getApplicationContext(),smessage, Toast.LENGTH_SHORT).show();
//                                }
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            removeSimpleProgressDialog();
//                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
//                        }
//                    }) {
//
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    //add string parameters
//                    params.put("Price", sprice);
//                    params.put("Category",sscategory);
//                    params.put("Date",sdate);
//                    params.put("Discription",sdiscription);
//                    params.put("Paymentmode",sspaymentmode);
//                    params.put("Image",spicture);
//
//                    return params;
//                }
//
//                /*
//                 *pass files using below method
//                 * */
//                @Override
//                protected Map<String, DataPart> getByteData() {
//                    Map<String, DataPart> params = new HashMap<>();
//                    params.put("filename", new DataPart(pdfname ,inputData));
//                    return params;
//                }
//            };
//
//
//            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
//                    0,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//            rQueue = Volley.newRequestQueue(getApplicationContext());
//            rQueue.add(volleyMultipartRequest);
//
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//
//
//    public byte[] getBytes(InputStream inputStream) throws IOException {
//        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
//        int bufferSize = 1024;
//        byte[] buffer = new byte[bufferSize];
//
//        int len = 0;
//        while ((len = inputStream.read(buffer)) != -1) {
//            byteBuffer.write(buffer, 0, len);
//        }
//        return byteBuffer.toByteArray();
//    }
//
//
//    public  void removeSimpleProgressDialog() {
//        try {
//            if (mProgressDialog != null) {
//                if (mProgressDialog.isShowing()) {
//                    mProgressDialog.dismiss();
//                    mProgressDialog = null;
//                }
//            }
//        } catch (IllegalArgumentException ie) {
//            Log.e("Log", "inside catch IllegalArgumentException");
//            ie.printStackTrace();
//        } catch (RuntimeException re) {
//            Log.e("Log", "inside catch RuntimeException");
//            re.printStackTrace();
//        } catch (Exception e) {
//            Log.e("Log", "Inside catch Exception");
//            e.printStackTrace();
//        }
//
//    }
//
//    public void showSimpleProgressDialog(Context context, String title,
//                                         String msg, boolean isCancelable) {
//        try {
//            if (mProgressDialog == null) {
//                mProgressDialog = ProgressDialog.show( context, title, msg );
//                mProgressDialog.setCancelable( isCancelable );
//            }
//            if (!mProgressDialog.isShowing()) {
//                mProgressDialog.show();
//            }
//        } catch (IllegalArgumentException ie) {
//            ie.printStackTrace();
//        } catch (RuntimeException re) {
//            re.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
